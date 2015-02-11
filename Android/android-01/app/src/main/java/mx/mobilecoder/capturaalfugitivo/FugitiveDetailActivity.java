package mx.mobilecoder.capturaalfugitivo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import mx.mobilecoder.capturaalfugitivo.model.Fugitive;


public class FugitiveDetailActivity extends Activity {

    // Constantes y referencias a elementos de la interfaz
    public static final String FUGITIVE_KEY = "FugitiveKey";
    private Fugitive mFugitive;

    private TextView mFugitiveNameTextView;
    private Button mCaptureFugitiveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fugitive_detail);

        // Obtener los elementos de la interfaz que vamos a llenar con los datos del Fugitivo.
        mFugitiveNameTextView = (TextView) findViewById(R.id.fugitiveNameTextView);
        mCaptureFugitiveButton = (Button) findViewById(R.id.captureFugitiveButton);

        // Recuperar al Fugitivo desde el Intent.
        Intent fromIntent = getIntent();
        mFugitive = fromIntent.getParcelableExtra(FUGITIVE_KEY);

        // Si el fugitivo ya fue capturado, ocultar el botón de "Capturar"
        if (mFugitive.mCaptured == 1) {
            mCaptureFugitiveButton.setVisibility(View.GONE);
        }

        // Mostrar el nombre del Fugitivo en el TextView
        mFugitiveNameTextView.setText(mFugitive.mName);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fugitive_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void captureFugitiveButtonClicked(View view)
    {
        /**
         * Se ejecuta este método cuando se hace click en el botón "Capturar"
         */


        mFugitive.mCaptured = 1; // Indicamos que el fugitivo ahora esta capturado.

        Intent resultIntent = new Intent();
        resultIntent.putExtra(FUGITIVE_KEY, mFugitive); // Generamos un nuevo Intento y asignamos el Fugitivo modificado como un Extra.

        setResult(1, resultIntent); // Se indica el resultado de la Actividad y se termina la misma
        finish();
    }
}
