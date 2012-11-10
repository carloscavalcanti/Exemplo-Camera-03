package com.example.exemplocamera;

import java.io.File;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private Button botao;
	private Uri imageUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		botao = (Button) findViewById(R.id.botao);
		botao.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		File photo = new File(Environment.getExternalStorageDirectory(),  "fotxinha.jpg");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
		imageUri = Uri.fromFile(photo);
		startActivityForResult(intent, 19);
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 19) {
			Uri selectedImage = imageUri;
			getContentResolver().notifyChange(selectedImage, null);
			ImageView imageView = (ImageView) findViewById(R.id.image);
			ContentResolver cr = getContentResolver();
			Bitmap bitmap;
			try {
				bitmap = android.provider.MediaStore.Images.Media
						.getBitmap(cr, selectedImage);

				imageView.setImageBitmap(bitmap);
				Toast.makeText(this, selectedImage.toString(),
						Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				Toast.makeText(this, "Falha", Toast.LENGTH_SHORT)
				.show();
				Log.e("Camera", e.toString());
			}

		}
	}

}