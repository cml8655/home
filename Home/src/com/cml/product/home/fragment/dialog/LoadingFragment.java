package com.cml.product.home.fragment.dialog;

import com.cml.product.home.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;

public class LoadingFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
		LayoutInflater inflator=LayoutInflater.from(getActivity());
		builder.setView(inflator.inflate(R.layout.dialog_loading, null));
		return builder.create();
	}
}
