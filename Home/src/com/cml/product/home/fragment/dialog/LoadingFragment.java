package com.cml.product.home.fragment.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.cml.product.home.R;

public class LoadingFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = new Dialog(getActivity(), R.style.loadingStyle);
		LayoutInflater inflator = LayoutInflater.from(getActivity());
		dialog.setContentView(inflator.inflate(R.layout.dialog_loading, null));
		return dialog;
	}
}
