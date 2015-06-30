package com.zonesion.tool;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public class StateFragment extends Fragment {
	Bundle saveState;

	public StateFragment() {
		super();
	}

	/*
	 * 切换回本身的fragment时调用的方法，依次是 onCreateView -> onActivityCreated -> onStart ->
	 * onResume
	 * 当Activity的onCreate方法返回时调用onActivityCreated
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Toast.makeText(getActivity(), "onActivityCreated方法被调用",
				Toast.LENGTH_SHORT).show();
		System.out.println("onActivityCreated方法被调用");
		// 恢复状态
		if (!restoreStateFromArguments()) {
			// First Time, Initialize something here
			onFirstTimeLaunched();
		}
	}

	protected void onFirstTimeLaunched() {

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// Save State Here
		saveStateToArguments();
	}

	// @Override
	// public void onStart() {
	// // TODO Auto-generated method stub
	// super.onStart();
	// Toast.makeText(getActivity(), "onStart方法被调用", Toast.LENGTH_SHORT)
	// .show();
	// System.out.println("onStart方法被调用");
	// }
	//
	// @Override
	// public void onResume() {
	// // TODO Auto-generated method stub
	// super.onResume();
	// Toast.makeText(getActivity(), "onResume方法被调用", Toast.LENGTH_SHORT)
	// .show();
	// System.out.println("onResume方法被调用");
	//
	// }

	/*
	 * 切换到其他fragment时调用的方法,依次是 onPause -> onStop -> onDestroyView
	 */
	// @Override
	// public void onPause() {
	// // TODO Auto-generated method stub
	// super.onPause();
	// Toast.makeText(getActivity(), "onPause方法被调用", Toast.LENGTH_SHORT)
	// .show();
	// System.out.println("onPause方法被调用");
	// }
	//
	// @Override
	// public void onStop() {
	// // TODO Auto-generated method stub
	// super.onStop();
	// Toast.makeText(getActivity(), "onStop方法被调用", Toast.LENGTH_SHORT).show();
	// System.out.println("onStop方法被调用");
	// }

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Toast.makeText(getActivity(), "onDestroyView方法被调用", Toast.LENGTH_SHORT)
				.show();
		System.out.println("onDestroyView方法被调用");
		// 这里保存状态
		saveStateToArguments();
	}

	// @Override
	// public void onDestroy() {
	// // TODO Auto-generated method stub
	// super.onDestroy();
	// Toast.makeText(getActivity(), "onDestroy方法被调用", Toast.LENGTH_SHORT)
	// .show();
	// System.out.println("onDestroy方法被调用");
	// }

	private void saveStateToArguments() {
		if (getView() != null)
			saveState = saveState();
		if (saveState != null) {
			Bundle b = getArguments();
			b.putBundle("state", saveState);
		}
	}

	private boolean restoreStateFromArguments() {
		Bundle b = getArguments();
		saveState = b.getBundle("state");
		if (saveState != null) {
			restoreState();
			return true;
		}
		return false;
	}

	/*
	 * 保存状态数据
	 */
	private Bundle saveState() {
		Bundle state = new Bundle();
		// state.putString("temperature", (String)
		// temperatureTextView.getText());
		onSaveState(state);
		return state;

	}
	protected void onSaveState(Bundle outState) {
		  
    }
	/*
	 * 取出状态数据
	 */
	private void restoreState() {
		if (saveState() != null) {
			// temperatureTextView.setText(saveState().getString("temperature"));
			onRestoreState(saveState);
		}

	}
	protected void onRestoreState(Bundle savedInstanceState) {
		  
    }
}
