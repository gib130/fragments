package course.labs.fragmentslab;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity implements
		FriendsFragment.SelectionListener {

	private static final String TAG = "Lab-Fragments";

	private FriendsFragment mFriendsFragment;
	private FeedFragment mFeedFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		// Если макет однопанельный, создаем FriendsFragment
		// и добавляем его к Activity

		if (!isInTwoPaneMode()) {
			
			mFriendsFragment = new FriendsFragment();
			//MainActivity.
			FragmentManager mFragmentManager = getFragmentManager();
			FragmentTransaction transaction = mFragmentManager.beginTransaction();
			transaction.replace(R.id.fragment_container, mFriendsFragment);
			transaction.commit();
			
			

		} else {

			// В противном случае, сохраняем ссылку в FeedFragment для дальнейшего использования

			mFeedFragment = (FeedFragment) getFragmentManager()
					.findFragmentById(R.id.feed_frag);
		}

	}

	// Если отсутствует fragment_container ID, тогда приложение находится в
	// двухпанельном режиме

	private boolean isInTwoPaneMode() {

		return findViewById(R.id.fragment_container) == null;
	
	}

	// Отображаем выбранную ленту Twitter

	public void onItemSelected(int position) {

		Log.i(TAG, "Entered onItemSelected(" + position + ")");

		// Если отсутствует экземпляр FeedFragment , тогда создаем его

		if (mFeedFragment == null)
			mFeedFragment = new FeedFragment();

		// Если в одно-панельном режиме, заменяем заменяем отдельный Fragment

		if (!isInTwoPaneMode()) {
			FragmentManager mFragmentManager = getFragmentManager();
			FragmentTransaction transaction = mFragmentManager.beginTransaction();
			transaction.replace(R.id.fragment_container, mFeedFragment);
			transaction.commit();




			// запускаем транзацию
			getFragmentManager().executePendingTransactions();

		}

		// Обновляем отображение ленты Twitter в FriendFragment
		mFeedFragment.updateFeedDisplay(position);

	}

}
