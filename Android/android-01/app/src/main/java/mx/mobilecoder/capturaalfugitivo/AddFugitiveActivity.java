package mx.mobilecoder.capturaalfugitivo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Esta Actividad es la que va a guardar fugitivos en la "base de datos."
 */

public class AddFugitiveActivity extends Activity {

    private EditText mFugitiveNameEditText; // Referencia al EditText en nuestro Layout

    public static final String FUGITIVE_NAME_KEY = "FugitiveNameKey"; // Constante para enviar el nombre del fugitivo en un Intent usando setResult

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fugitive);

        mFugitiveNameEditText = (EditText) findViewById(R.id.fugitiveNameEditText); // Obtener el EditText desde el Layout
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_fugitive, menu);
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

    public void addFugitiveButtonClicked(View view) {
        /**
         * Este metodo se ejecuta cuando el usuario hace click en el botón definido en
         * la interfaz.
         */
        String fugitiveName = mFugitiveNameEditText.getText().toString(); // Obtener el texto

        if (fugitiveName.isEmpty()) {
            // Validar que el String no este vacío. En ese caso, mostrar alerta.
            Toast.makeText(this, getString(R.string.add_fugitive_empty_name_error_message), Toast.LENGTH_LONG).show();
        }else{
            // Si el nombre existe, enviarlo como resultado de la Actividad y terminar la misma.
            Intent resultIntent = new Intent();
            resultIntent.putExtra(FUGITIVE_NAME_KEY, fugitiveName);
            setResult(1, resultIntent);
            finish();
        }
    }
}
