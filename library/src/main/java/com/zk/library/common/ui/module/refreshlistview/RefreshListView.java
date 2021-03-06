/**
 * @file XListView.java
 * @package me.maxwin.view
 * @create Mar 18, 2012 6:28:41 PM
 * @author Maxwin
 * @description An ListView support (a) Pull down to refresh, (b) Pull up to load more.
 * 		Implement IXListViewListener, and see stopRefresh() / stopLoadMore().
 */
package com.zk.library.common.ui.module.refreshlistview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.zk.library.R;


public class RefreshListView extends ListView implements OnScrollListener {
	private float mFirstY = -1;
	private float mLastY = -1; // save event y
	private Scroller mScroller; // used for scroll back
	private OnScrollListener mScrollListener; // user's scroll listener

	// the interface to trigger refresh and load more.
	private OnRefreshListViewListener mListViewListener;

	// -- header view
	private RefreshListViewHeader mHeaderView;
	// header view content, use it to calculate the Header's height. And hide it
	// when disable pull refresh.
	private RelativeLayout mHeaderViewContent;
	private TextView mHeaderHintView;
	private TextView mHeaderTimeView;
	private int mHeaderViewHeight; // header view's height
	private boolean mEnablePullRefresh = true;
	private boolean mPullRefreshing = false; // is refreashing.

	// -- footer view
	private RefreshListViewFooter mFooterView;
	private boolean mEnablePullLoad;
	private boolean mPullLoading;
	private boolean mIsFooterReady = false;

	// total list items, used to detect is at the bottom of listview.
	private int mTotalItemCount;

	// for mScroller, scroll back from header or footer.
	private int mScrollBack;
	private final static int SCROLLBACK_HEADER = 0;
	private final static int SCROLLBACK_FOOTER = 1;

	private final static int SCROLL_DURATION = 400; // scroll back duration
	private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >= 50px
	// at bottom, trigger
	// load more.
	private final static float OFFSET_RADIO = 1.8f; // support iOS like pull
	// feature.

	//scale the pull to refresh distance, little touch and bigger pull distance  ---add by sea 15/6/3
	private  float scalePull = 1f;

	//set scalePull ---add by sea 15/6/3
	public void setScalePull(float scalePull) {
		this.scalePull = scalePull;
	}

	//set refresh header hint text  at refreshing state
	public void setRefreshingHeaderHint(String hintText){
		if (mHeaderView !=null) mHeaderView.setStateRefreshingHint(hintText);
	}

	//set refresh header hint text
	public void setHeaderHint(String hintText){
		if (mHeaderView !=null) mHeaderView.setHeanderHintText(hintText);
	}

	public boolean isRefreshing() {
		return mPullRefreshing;
	}

	/**
	 * @param context
	 */
	public RefreshListView(Context context) {
		super(context);
		initWithContext(context);
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initWithContext(context);
	}

	public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initWithContext(context);
	}

	private void initWithContext(Context context) {
		mScroller = new Scroller(context, new DecelerateInterpolator());
		// RefreshListView need the scroll event, and it will dispatch the event to
		// user's listener (as a proxy).
		super.setOnScrollListener(this);

		// init header view
		mHeaderView = new RefreshListViewHeader(context);
		mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById(R.id.xlistview_header_content);
		mHeaderHintView = (TextView) mHeaderView.findViewById(R.id.xlistview_header_hint_textview);
		mHeaderTimeView = (TextView) mHeaderView.findViewById(R.id.xlistview_header_time);
		addHeaderView(mHeaderView);

		// init footer view
		mFooterView = new RefreshListViewFooter(context);

		// init header height
		ViewTreeObserver observer = mHeaderView.getViewTreeObserver();
		observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
			@Override
			public void onGlobalLayout() {
				mHeaderViewHeight = mHeaderViewContent.getHeight();
				ViewTreeObserver observer = getViewTreeObserver();
				if (null != observer) {
					if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
						observer.removeGlobalOnLayoutListener(this);
					} else {
						observer.removeOnGlobalLayoutListener(this);
					}
				}
			}
		});

	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		// make sure RefreshListViewFooter is the last footer view, and only add once.
		if (mIsFooterReady == false) {
			mIsFooterReady = true;
			addFooterView(mFooterView);
		}
		super.setAdapter(adapter);
	}

	@Override
	public boolean removeFooterView(View v) {

		super.removeFooterView(mFooterView);

		return false;
	}

	/**
	 * enable or disable pull down refresh feature.
	 *
	 * @param enable
	 */
	public void setPullRefreshEnable(boolean enable) {
		mEnablePullRefresh = enable;
		if (!mEnablePullRefresh) { // disable, hide the content
			mHeaderViewContent.setVisibility(View.INVISIBLE);
		} else {
			mHeaderViewContent.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * enable or disable pull up load more feature.
	 *
	 * @param enable
	 */
	public void setPullLoadEnable(boolean enable) {
		mEnablePullLoad = enable;
		if (!mEnablePullLoad) {
			mFooterView.hide();
			mFooterView.setOnClickListener(null);
			//make sure "pull up" don't show a line in bottom when listview with one page
			setFooterDividersEnabled(false);
		} else {
			mPullLoading = false;
			mFooterView.show();
			mFooterView.setState(RefreshListViewFooter.STATE_NORMAL);
			//make sure "pull up" don't show a line in bottom when listview with one page
			setFooterDividersEnabled(true);
			// both "pull up" and "click" will invoke load more.
			mFooterView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startLoadMore();
				}
			});
		}
	}

	/**
	 * 手动触发刷新动作，如果当前正在刷新则该方法不执行任何操作。
	 * 调用该方法后列表将处理刷新模式，并且触发 onRefresh 事件。
	 */
	public void startRefresh(){
		if (mPullRefreshing){
			return;
		}

		if (mEnablePullRefresh) {
			smoothScrollToPosition(0);
			mHeaderView.setVisiableHeight(mHeaderViewHeight + 1);
			mPullRefreshing = true;
			mHeaderView.setState(RefreshListViewHeader.STATE_REFRESHING);
			if (mListViewListener != null) {
				mListViewListener.onRefresh();
			}
		}

		resetHeaderHeight();
	}

	/**
	 * 获取底部高度
	 */
	public int getBottomHeight(){
		int bottomW = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int bottomH = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		mFooterView.measure(bottomW, bottomH);
		return mFooterView.getMeasuredHeight();
	}
	/**
	 * Auto call back refresh.
	 */
	public void autoRefresh() {

		if (mPullRefreshing) return;

		mHeaderView.setVisiableHeight(mHeaderViewHeight);

		if (mEnablePullRefresh && !mPullRefreshing) {
			// update the arrow image not refreshing
			if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
				mHeaderView.setState(RefreshListViewHeader.STATE_READY);
			} else {
				mHeaderView.setState(RefreshListViewHeader.STATE_NORMAL);
			}
		}

		mPullRefreshing = true;
		mHeaderView.setState(RefreshListViewHeader.STATE_REFRESHING);

	}

	/**
	 * stop refresh, reset header view.
	 */
	public void stopRefresh() {
		if (mPullRefreshing == true) {
			mPullRefreshing = false;
			resetHeaderHeight();
		}
	}

	/**
	 * stop load more, reset footer view.
	 */
	public void stopLoadMore() {
		if (mPullLoading == true) {
			mPullLoading = false;
			mFooterView.setState(RefreshListViewFooter.STATE_NORMAL);
		}
	}

	/**
	 * set last refresh time
	 *
	 * @param time
	 */
	public void setRefreshTime(String time) {
		mHeaderTimeView.setText(time);
	}

	private void invokeOnScrolling() {
		if (mScrollListener instanceof OnXScrollListener) {
			OnXScrollListener l = (OnXScrollListener) mScrollListener;
			l.onXScrolling(this);
		}
	}

	private void updateHeaderHeight(float delta) {
		mHeaderView.setVisiableHeight((int) delta
				+ mHeaderView.getVisiableHeight());
		if (mEnablePullRefresh && !mPullRefreshing) { // 未处于刷新状态，更新箭头
			if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
				mHeaderView.setState(RefreshListViewHeader.STATE_READY);
			} else {
				mHeaderView.setState(RefreshListViewHeader.STATE_NORMAL);
			}
		}
		setSelection(0); // scroll to top each time
	}

	/**
	 * reset header view's height.
	 */
	private void resetHeaderHeight() {
		int height = mHeaderView.getVisiableHeight();
		if (height == 0) // not visible.
			return;
		// refreshing and header isn't shown fully. do nothing.
		if (mPullRefreshing && height <= mHeaderViewHeight) {
			return;
		}
		int finalHeight = 0; // default: scroll back to dismiss header.
		// is refreshing, just scroll back to show all the header.
		if (mPullRefreshing && height > mHeaderViewHeight) {
			finalHeight = mHeaderViewHeight;
		}
		mScrollBack = SCROLLBACK_HEADER;
		mScroller.startScroll(0, height, 0, finalHeight - height,
				SCROLL_DURATION);
		// trigger computeScroll
		invalidate();
	}

	private void updateFooterHeight(float delta) {
		int height = mFooterView.getBottomMargin() + (int) delta;
		if (mEnablePullLoad && !mPullLoading) {
			if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load
				// more.
				mFooterView.setState(RefreshListViewFooter.STATE_READY);
			} else {
				mFooterView.setState(RefreshListViewFooter.STATE_NORMAL);
			}
		}
		mFooterView.setBottomMargin(height);
		setSelection(mTotalItemCount - 1); // scroll to bottom
	}

	private void resetFooterHeight() {
		int bottomMargin = mFooterView.getBottomMargin();
		if (bottomMargin > 0) {
			mScrollBack = SCROLLBACK_FOOTER;
			mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
					SCROLL_DURATION);
			invalidate();
		}
	}

	private void startLoadMore() {
		mPullLoading = true;
		mFooterView.setState(RefreshListViewFooter.STATE_LOADING);
		if (mListViewListener != null) {
			mListViewListener.onLoadMore();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		//FixBug by Michael Ge: 修正只有一页时“加载更多”显示不正常的问題
		if (mLastY == -1) {
			mLastY  = ev.getRawY();
			mFirstY = ev.getRawY();
		}

		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mLastY = ev.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				final float deltaY = ev.getRawY() - mLastY;
				mLastY = ev.getRawY();
				if (getFirstVisiblePosition() == 0
						&& (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
					// the first item is showing, header has shown or pull down.
					//add scale pull touch distance   ---add by sea 15/6/3
					updateHeaderHeight(deltaY / OFFSET_RADIO * scalePull);
					invokeOnScrolling();
				} else if (getLastVisiblePosition() == mTotalItemCount - 1
						&& (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
					// last item, already pulled up or want to pull up.
					updateFooterHeight(-deltaY / OFFSET_RADIO);
				}
				break;
			default:
				float direction = ev.getRawY() - mFirstY;
				if (getFirstVisiblePosition() == 0 && direction > 0) {
					// invoke refresh
					if (mEnablePullRefresh && !mPullRefreshing
							&& mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
						mPullRefreshing = true;
						mHeaderView.setState(RefreshListViewHeader.STATE_REFRESHING);
						if (mListViewListener != null) {
							mListViewListener.onRefresh();
						}
					}
					resetHeaderHeight();
				}

				if ((getLastVisiblePosition() == mTotalItemCount - 1) && direction < 0) {
					// invoke load more.
					if (mEnablePullLoad
							&& mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA
							&& !mPullLoading) {
						startLoadMore();
					}
					resetFooterHeight();
				}

				mLastY  = -1; // reset
				mFirstY = -1;
				break;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			if (mScrollBack == SCROLLBACK_HEADER) {
				mHeaderView.setVisiableHeight(mScroller.getCurrY());
			} else {
				mFooterView.setBottomMargin(mScroller.getCurrY());
			}
			postInvalidate();
			invokeOnScrolling();
		}
		super.computeScroll();
	}

	@Override
	public void setOnScrollListener(OnScrollListener l) {
		mScrollListener = l;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (mScrollListener != null) {
			mScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
		// send to user's listener
		mTotalItemCount = totalItemCount;
		if (mScrollListener != null) {
			mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
					totalItemCount);
		}
	}

	public void setOnRefreshListViewListener(OnRefreshListViewListener l) {
		mListViewListener = l;
	}

	/**
	 * you can listen ListView.OnScrollListener or this one. it will invoke
	 * onXScrolling when header/footer scroll back.
	 */
	public interface OnXScrollListener extends OnScrollListener {
		public void onXScrolling(View view);
	}

	/**
	 * implements this interface to get refresh/load more event.
	 */
	public interface OnRefreshListViewListener {
		public void onRefresh();

		public void onLoadMore();
	}
}
