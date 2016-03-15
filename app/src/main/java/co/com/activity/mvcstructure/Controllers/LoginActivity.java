package co.com.activity.mvcstructure.Controllers;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;

import co.com.activity.mvcstructure.Components.Beans.UsuariosBeans;
import co.com.activity.mvcstructure.Models.Usuarios;
import co.com.activity.mvcstructure.R;
import co.com.activity.mvcstructure.Util.Useful;

public class LoginActivity extends AppCompatActivity {

    private final Context CONTEXT = LoginActivity.this;
    private Bundle bundle;
    private Useful use;
    private Usuarios usuarios;
    private ListView listaUsuarios;
    private UserAdapter adapter;
    private ArrayList<UsuariosBeans> usuariosBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bundle = getIntent().getExtras();
        listaUsuarios = (ListView) findViewById(R.id.lstUsuarios);
        usuariosBD = new ArrayList<UsuariosBeans>();
        adapter = new UserAdapter(CONTEXT, R.layout.lista_usuarios, usuariosBD);
        listaUsuarios.setAdapter(adapter);
        usuariosBD.clear();
        adapter.clear();
        /*TextView saludo = (TextView) findViewById(R.id.hello);
        saludo.setText(bundle.getString("Saludo"));*/
        use = new Useful(CONTEXT);
        usuarios = new Usuarios(use.getDbConn());
        usuarios.truncateTable();
        usuarios.insertInto("IdUsuario,Contrasena", "mcano", 1047);
        usuarios.insertInto("IdUsuario,Contrasena", "sposada", 1020);
        Integer _id = Integer.parseInt(usuarios.search("_id", "IdUsuario", "sposada"));
        usuarios.updateRecord("Contrasena", 8910, _id);
        usuarios.updateWhereRecord("Contrasena", "111213", "IdUsuario", "mcano");

        llenarLista();
    }

    public void llenarLista() {
        Hashtable result = usuarios.searchAll();
        for (int i = 0; i < result.size(); i++) {
            Hashtable data = (Hashtable) result.get(i);
            usuariosBD.add(new UsuariosBeans((String) data.get("IdUsuario"),
                    (String) data.get("Contrasena")));
            adapter.notifyDataSetChanged();
        }
        listaUsuarios.setAdapter(adapter);

        onClickList();
    }

    public void onClickList(){
        listaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CONTEXT,Integer.toString(position),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void btnAceptar(View view) {
        EditText edtNombre = (EditText) findViewById(R.id.edtNombre);
        AlertDialog.Builder dlgAceptar = new AlertDialog.Builder(CONTEXT);
        dlgAceptar.setTitle(R.string.app_name);
        dlgAceptar.setMessage(edtNombre.getText());
        dlgAceptar.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dlgAceptar.show();
    }


    private class UserAdapter extends ArrayAdapter {

        private int r;
        private ArrayList<UsuariosBeans> users;
        private View itemView;

        public UserAdapter(Context context, int resource, ArrayList<UsuariosBeans> objects) {
            super(context, resource, objects);
            this.r = resource;
            this.users = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            this.itemView = convertView;
            ViewHolder holder = null;
            if(this.itemView==null){
                LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                itemView = vi.inflate(r, null);

                holder = new ViewHolder();
                holder.txtUsuario = (TextView) itemView.findViewById(R.id.txtUsuario);
                holder.txtContrasena = (TextView) itemView.findViewById(R.id.txtContrasena);

                itemView.setTag(holder);
            } else {
                holder = (ViewHolder) this.itemView.getTag();
            }

            UsuariosBeans user = this.users.get(position);

            holder.txtUsuario.setText(user.getUsuarios());
            holder.txtContrasena.setText(user.getContrasena());

            return this.itemView;
        }

        private class ViewHolder {
            public TextView txtUsuario;
            public TextView txtContrasena;
        }
    }
}
