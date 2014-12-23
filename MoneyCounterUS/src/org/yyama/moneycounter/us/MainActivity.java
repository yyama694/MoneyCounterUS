package org.yyama.moneycounter.us;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/*
 * 
 */
public class MainActivity extends ActionBarActivity implements OnClickListener {

	int[] kind = { 1, 5, 10, 50, 100, 500, 1000, 2000, 5000, 10000 };
	Map<Integer, Data> data = new HashMap<Integer, Data>();
	CountDialog cDialog = new CountDialog();
	Map<Integer, Integer> idMapNum = new HashMap<Integer, Integer>();
	Map<Integer, Integer> idMapSum = new HashMap<Integer, Integer>();
	CountDialog countDialog;
	int[] numIds = { R.id.Num1, R.id.Num5, R.id.Num10, R.id.Num50, R.id.Num100,
			R.id.Num500, R.id.Num1000, R.id.Num2000, R.id.Num5000,
			R.id.Num10000 };
	int[] sumIds = { R.id.Sum1, R.id.Sum5, R.id.Sum10, R.id.Sum50, R.id.Sum100,
			R.id.Sum500, R.id.Sum1000, R.id.Sum2000, R.id.Sum5000,
			R.id.Sum10000 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Log.d("fukuchi", "onCreate");
		for (int i : kind) {
			data.put(i, new Data(0, 0));
		}

		((Button) findViewById(R.id.btnClear)).setOnClickListener(this);

		((TextView) findViewById(R.id.Text10000)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text5000)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text2000)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text1000)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text500)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text100)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text50)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text10)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text5)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text1)).setOnClickListener(this);

		idMapNum.put(R.id.Text1, R.id.Num1);
		idMapNum.put(R.id.Text5, R.id.Num5);
		idMapNum.put(R.id.Text10, R.id.Num10);
		idMapNum.put(R.id.Text50, R.id.Num50);
		idMapNum.put(R.id.Text100, R.id.Num100);
		idMapNum.put(R.id.Text500, R.id.Num500);
		idMapNum.put(R.id.Text1000, R.id.Num1000);
		idMapNum.put(R.id.Text2000, R.id.Num2000);
		idMapNum.put(R.id.Text5000, R.id.Num5000);
		idMapNum.put(R.id.Text10000, R.id.Num10000);

		idMapSum.put(R.id.Text1, R.id.Sum1);
		idMapSum.put(R.id.Text5, R.id.Sum5);
		idMapSum.put(R.id.Text10, R.id.Sum10);
		idMapSum.put(R.id.Text50, R.id.Sum50);
		idMapSum.put(R.id.Text100, R.id.Sum100);
		idMapSum.put(R.id.Text500, R.id.Sum500);
		idMapSum.put(R.id.Text1000, R.id.Sum1000);
		idMapSum.put(R.id.Text2000, R.id.Sum2000);
		idMapSum.put(R.id.Text5000, R.id.Sum5000);
		idMapSum.put(R.id.Text10000, R.id.Sum10000);
		addSetting();
		add2Init();
		add2Setting();

		load(FILE_NAME);
		draw();
	}

	private AdView adView;

	private void addSetting() {
		// adView を作成する
		adView = new AdView(this);
		adView.setAdUnitId("ca-app-pub-2505812570403600/7142907370");
		adView.setAdSize(AdSize.BANNER);

		// 属性 android:id="@+id/mainLayout" が与えられているものとして
		// LinearLayout をルックアップする
		LinearLayout layout = (LinearLayout) findViewById(R.id.adSpace);

		// adView を追加する
		layout.addView(adView);

		// 一般的なリクエストを行う
		AdRequest adRequest = new AdRequest.Builder().addTestDevice(
				"2D6B2CDFA13324C63449E43857621522").build();
		// AdRequest adRequest = new AdRequest.Builder().build();

		// 広告リクエストを行って adView を読み込む
		adView.loadAd(adRequest);

	}

	private InterstitialAd interstitial;

	// 全画面広告の方。
	private void add2Init() {
		// インタースティシャルを作成する。
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId("ca-app-pub-2505812570403600/9562186572");

		// Set the AdListener.
		interstitial.setAdListener(new AdListener() {
			@Override
			public void onAdClosed() {
				add2Setting();
			}
		});
	}

	// 全画面広告の方。1回表示するたびにロードしなおす必要があるみたい。
	private void add2Setting() {
		if (!interstitial.isLoaded()) {
			Log.d("fukuchi", "未ロードのため、リクエストします。");
			// 広告リクエストを作成する。
			AdRequest adRequest = new AdRequest.Builder().addTestDevice(
					"2D6B2CDFA13324C63449E43857621522").build();
			// インタースティシャルの読み込みを開始する。
			interstitial.loadAd(adRequest);
		} else {
		}
	}

	private void draw() {
		int sum = 0;
		for (int i = 0; i < numIds.length; i++) {
			// Log.d("fukuchi", ((TextView) findViewById(numIds[i])).getText()
			// .toString());
			((TextView) findViewById(numIds[i])).setText(" "
					+ String.format("%,d", data.get(kind[i]).num) + " 枚");
			((TextView) findViewById(sumIds[i])).setText(String.format("%, d",
					data.get(kind[i]).sum) + " 円");
			sum += data.get(kind[i]).sum;
		}
		((TextView) findViewById(R.id.allSum)).setText(String.format("%,d 円",
				sum));
	}

	@Override
	public void onClick(final View v) {
		add2Setting();
		if (v.getId() == R.id.btnClear) {
			Clear();
		} else {
			showCountDialog(v);
		}
	}

	private void save() {
		OutputStream out;
		try {
			out = openFileOutput(FILE_NAME, MODE_PRIVATE);
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(out,
					"UTF-8"));
			// 追記する
			for (int i : kind) {
				writer.append(i + "," + data.get(i).num + "," + data.get(i).sum
						+ System.lineSeparator());
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final static String FILE_NAME = "CountDialog.dat";

	private void load(String fileName) {
		InputStream in;
		String lineBuffer;

		try {
			in = openFileInput(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					in, "UTF-8"));
			while ((lineBuffer = reader.readLine()) != null) {
				String[] strs = lineBuffer.split(",");
				int key = Integer.parseInt(strs[0]);
				data.get(key).num = Integer.parseInt(strs[1]);
				data.get(key).sum = Integer.parseInt(strs[2]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showCountDialog(final View v) {
		LayoutInflater li = LayoutInflater.from(this);
		final View dialog = li.inflate(R.layout.dialog, null);
		int now = getNow(v.getId());
		((TextView) dialog.findViewById(R.id.edit))
				.setText(String.valueOf(now));
		((TextView) dialog.findViewById(R.id.edit))
				.setText(String.valueOf(now));
		AlertDialog.Builder builder = new Builder(this);
		builder.setView(dialog);
		builder.setTitle(((TextView) v).getText() + "の枚数");
		builder.setNegativeButton("キャンセル",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface df, int which) {
				String editStr = ((EditText) dialog.findViewById(R.id.edit))
						.getText().toString();
				int num;
				// 数値判定
				try {
					num = Integer.parseInt(editStr);
				} catch (NumberFormatException e) {
					return;
				}
				int kind = getKind(((TextView) v).getText().toString());
				data.get(kind).num = num;
				data.get(kind).sum = num * kind;
				save();
				draw();
			}
		});
		((Button) dialog.findViewById(R.id.btnPlus1))
				.setOnClickListener(cDialog);
		((Button) dialog.findViewById(R.id.btnPlus5))
				.setOnClickListener(cDialog);
		((Button) dialog.findViewById(R.id.btnPlus10))
				.setOnClickListener(cDialog);
		((Button) dialog.findViewById(R.id.btnPlus50))
				.setOnClickListener(cDialog);
		((Button) dialog.findViewById(R.id.btnMinus1))
				.setOnClickListener(cDialog);
		((Button) dialog.findViewById(R.id.btnMinus5))
				.setOnClickListener(cDialog);
		((Button) dialog.findViewById(R.id.btnMinus10))
				.setOnClickListener(cDialog);
		((Button) dialog.findViewById(R.id.btnMinus50))
				.setOnClickListener(cDialog);
		builder.show();
	}

	private void Clear() {
		// 確認ダイアログの生成
		AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
		alertDlg.setTitle("全クリア確認");
		alertDlg.setMessage("全てクリアしてもよろしいですか？");
		alertDlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				for (int i : kind) {
					data.get(i).num = 0;
					data.get(i).sum = 0;
				}
				save();
				draw();
			}
		});
		alertDlg.setNegativeButton("キャンセル",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		alertDlg.create().show();
	}

	private int getKind(String str) {
		String s = str.replace("円", "").replace(",", "").replace(" ", "");
		return Integer.parseInt(s);
	}

	private int getNow(int id) {
		String str = ((TextView) findViewById(idMapNum.get(id))).getText()
				.toString().replace(",", "");
		Log.d("fukuchi", str);
		int now = Integer.parseInt(str.substring(1, str.indexOf(" ", 2)));
		Log.d("fukuchi", String.valueOf(now));
		return now;
	}

	@Override
	public void onPause() {
		adView.pause();
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		adView.resume();
	}

	@Override
	public void onDestroy() {
		adView.destroy();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	public static final int REQUEST_CD = 100;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.screenshot:
			Screenshot.saveScreen(this);
			if (interstitial.isLoaded()) {
				Log.d("fukuchi", "インターステシャルはロードされています。");
				interstitial.show();
			} else {
				Log.d("fukuchi", "インターステシャルはロードされていません。");
			}
			add2Setting();
			break;
		case R.id.file_save:
			// ファイル保存
			saveLocalFile();
			break;
		case R.id.file_open:
			// ファイル選択アクティビティを表示
			Intent intent = new Intent(this, LocalFileActivity.class);
			this.startActivityForResult(intent, REQUEST_CD);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void saveLocalFile() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final EditText edit = new EditText(this);
		edit.setInputType(InputType.TYPE_CLASS_TEXT);
		builder.setView(edit);
		builder.setTitle(this.getString(R.string.please_memo));

		builder.setNegativeButton(R.string.cancel,
				new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		builder.setPositiveButton(R.string.saveFile,
				new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String fileName = String.format(
								"MC_%1$tY%1$tm%1$td_%1$tH%1$tM%1$tS_%2$s.txt",
								Calendar.getInstance(), edit.getText());
						PrintWriter pw = null;
						try {
							pw = new PrintWriter(MainActivity.this
									.openFileOutput(fileName,
											Activity.MODE_PRIVATE));
							// 追記する
							for (int i : kind) {
								pw.append(i + "," + data.get(i).num + ","
										+ data.get(i).sum
										+ System.lineSeparator());
							}
							pw.flush();
							Toast.makeText(MainActivity.this,
									R.string.has_been_saved, Toast.LENGTH_LONG)
									.show();
						} catch (Exception e) {
							Toast.makeText(MainActivity.this,
									R.string.failed_to_save, Toast.LENGTH_LONG)
									.show();
						} finally {
							if (!(pw == null)) {
								pw.close();
							}
						}
					}
				});
		final AlertDialog dialog = builder.create();
		edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					dialog.getButton(AlertDialog.BUTTON_POSITIVE)
							.performClick();
				}
				return true;
			}
		});
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				// ソフトキーボードを出す
				InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.showSoftInput(edit, 0);
			}
		});
		dialog.show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CD) {
			switch (resultCode) {
			case RESULT_OK:
				try {
					load(data.getStringExtra("file_name"));
					save();
					draw();
					Toast.makeText(
							this,
							getString(R.string.opend_file)
									+ System.lineSeparator()
									+ data.getStringExtra("file_title"),
							Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(this, getString(R.string.file_open_error),
							Toast.LENGTH_LONG).show();
				}

				break;
			case RESULT_CANCELED:
				break;
			default:
				break;
			}
		}
	}
}
