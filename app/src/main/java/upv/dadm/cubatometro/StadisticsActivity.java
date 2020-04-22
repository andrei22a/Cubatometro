package upv.dadm.cubatometro;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import upv.dadm.cubatometro.Database.DAO;
import upv.dadm.cubatometro.Listeners.RegistrosListener;
import upv.dadm.cubatometro.entidades.Registro;

public class StadisticsActivity extends AppCompatActivity {

    private DAO dao = new DAO();
    private FirebaseAuth mAuth;

    private TextView selectedDateRangeLabel;
    private EditText initialDateInput;
    private EditText finalDateInput;
    private Button searchButton;
    private LineChart lineChart;
    private ProgressBar progressBar;

    private int totalScore;
    private String initialDate;
    private String finalDate;

    String username;
    private String groupID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadistics);

        groupID = getSharedPreferences("groupDetails", MODE_PRIVATE).getString("groupID", "");

        mAuth = FirebaseAuth.getInstance();
        username = mAuth.getCurrentUser().getDisplayName();

        selectedDateRangeLabel = findViewById(R.id.selected_dates_textView);
        initialDateInput = findViewById(R.id.initial_date_editText);
        finalDateInput = findViewById(R.id.final_date_editText);
        searchButton = findViewById(R.id.search_button);
        lineChart = findViewById(R.id.line_chart);
        progressBar = findViewById(R.id.progressBar_stadistics);

        ArrayList<String> xLabels = new ArrayList<>();
        xLabels.add("");
        xLabels.add("Botellas");
        xLabels.add("1/2 Botellas");
        xLabels.add("Litros");
        xLabels.add("Jarras");
        xLabels.add("Latas");
        xLabels.add("Vino");
        xLabels.add("Chupitos");


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setCenterAxisLabels(false);
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setLabelCount(xLabels.size());
        xAxis.setGranularity(1);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));


       /* searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!initialDateInput.getText().equals("") && !finalDateInput.getText().equals("")){
                    if(initialDateInput.getText().toString().matches("^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$") && finalDateInput.getText().toString().matches("^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$")){
                        initialDate = initialDateInput.getText().toString();
                        finalDate = finalDateInput.getText().toString();

                        progressBar.setVisibility(View.VISIBLE);



                        dao.getRegistros(mAuth.getCurrentUser().getUid(), groupID, new RegistrosListener() {
                            @Override
                            public void onRegistrosReceived(HashMap<String, List<Registro>> registrosGrupo) throws ParseException {
                                //TODO poner cuando se carguen los datos en las graficas
                                closeKeyboard();
                                clearDateInputs();
                                changeLabel(initialDate, finalDate);
                                cargarDatos(registrosGrupo);
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Throwable error) {

                            }
                        });



                    } else {
                        toastMessage("Error: el formato de las fechas debe ser dd/MM/yyyy");
                    }
                } else {
                    toastMessage("Rellena los campos fecha inicial y fecha final");
                }
            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_stadistics, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return false;
    }

    public void onClickSearchButton(View view){
        if(!initialDateInput.getText().toString().equals("") && !finalDateInput.getText().toString().equals("")){
            if(initialDateInput.getText().toString().matches("^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$") && finalDateInput.getText().toString().matches("^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$")){
                initialDate = initialDateInput.getText().toString();
                finalDate = finalDateInput.getText().toString();

                progressBar.setVisibility(View.VISIBLE);



                dao.getRegistros(mAuth.getCurrentUser().getUid(), groupID, new RegistrosListener() {
                    @Override
                    public void onRegistrosReceived(HashMap<String, List<Registro>> registrosGrupo) throws ParseException {
                        //TODO poner cuando se carguen los datos en las graficas
                        closeKeyboard();
                        clearDateInputs();
                        changeLabel(initialDate, finalDate);
                        cargarDatos(registrosGrupo);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable error) {

                    }
                });



            } else {
                toastMessage("Error: el formato de las fechas debe ser dd/MM/yyyy");
            }
        } else {
            toastMessage("Rellena los campos fecha inicial y fecha final");
        }
    }

    public boolean fechaEntreRango(String fechaRegistro, String fechaInicial, String fechaFinal) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        Date fechaInicialDate = format.parse(fechaInicial);
        Date fechaFinalDate = format.parse(fechaFinal);
        Date fechaRegistroDate = format.parse(fechaRegistro);

        return fechaRegistroDate.compareTo(fechaFinalDate) <= 0 && fechaRegistroDate.compareTo(fechaInicialDate) >= 0;
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void cargarDatos(HashMap<String, List<Registro>> registros) throws ParseException {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        for(Map.Entry<String, List<Registro>> entry : registros.entrySet()) {
            float numBotellas = 0, numMediasBotellas = 0, numLitros = 0, numJarras = 0, numLatas = 0, numVinos = 0, numChupitos = 0;
            List<Registro> registrosUsuario = entry.getValue();
            String nombreUsuario = entry.getKey();
            ArrayList<Entry> yValues = new ArrayList<>();


            for(Registro registro : registrosUsuario) {
                if(fechaEntreRango(registro.getFecha(), initialDate, finalDate)){
                    numBotellas += registro.getNumBotellas();
                    numMediasBotellas += registro.getNumMediasBotellas();
                    numLitros += registro.getNumLitrosCerveza();
                    numJarras += registro.getNumJarrasCerveza();
                    numLatas += registro.getNumLatasCerveza();
                    numVinos += registro.getNumBotellasVino();
                    numChupitos += registro.getNumChupitos();
                }
            }

            yValues.add(new Entry(1, numBotellas));
            yValues.add(new Entry(2, numMediasBotellas));
            yValues.add(new Entry(3, numLitros));
            yValues.add(new Entry(4, numJarras));
            yValues.add(new Entry(5, numLatas));
            yValues.add(new Entry(6, numVinos));
            yValues.add(new Entry(7, numChupitos));

            LineDataSet lineDataSet = new LineDataSet(yValues, nombreUsuario);
            lineDataSet.setColor(Color.rgb(randomColorGenerator(), randomColorGenerator(), randomColorGenerator()));
            dataSets.add(lineDataSet);
        }

        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);

    }

    public int randomColorGenerator(){
        Random generator = new Random();
        return generator.nextInt(256);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void changeLabel(String initialDate, String finalDate){
        selectedDateRangeLabel.setText(getString(R.string.selected_dates_range_label).replace("%1s", initialDate).replace("%2s", finalDate));
        selectedDateRangeLabel.setVisibility(View.VISIBLE);
    }

    private void clearDateInputs(){
        initialDateInput.getText().clear();
        finalDateInput.getText().clear();
    }
}
