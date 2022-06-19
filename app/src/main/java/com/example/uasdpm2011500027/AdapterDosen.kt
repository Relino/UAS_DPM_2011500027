package com.example.uasdpm2011500027

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class AdapterDosen  (
    private val getContext: Context,
    private val customListItem: ArrayList<DataPengampu>
): ArrayAdapter<DataPengampu>(getContext, 0, customListItem) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listLayout = convertView
        val holder: ViewHolder
        if (listLayout == null) {
            val infLateList = (getContext as Activity).layoutInflater
            listLayout = infLateList.inflate(R.layout.activity_item, parent, false)
            holder = ViewHolder()
            with(holder) {
                tvNmDosen = listLayout.findViewById(R.id.tvNmDosen)
                tvnidn = listLayout.findViewById(R.id.tvnidn)
                tvStudi = listLayout.findViewById(R.id.tvStudi)
                btnEdit = listLayout.findViewById(R.id.btnEdit)
                btnHapus = listLayout.findViewById(R.id.btnHapus)
            }
            listLayout.tag = holder
        }else
            holder = listLayout.tag as ViewHolder
        val listItem = customListItem[position]
        holder.tvNmDosen!!.setText(listItem.nmDosen)
        holder.tvnidn!!.setText(listItem.nidn)
        holder.tvStudi!!.setText(listItem.programstudi)

        holder.btnEdit!!.setOnClickListener{
            val i = Intent(context, EntriDataDosen::class.java)
            i.putExtra("nidn", listItem.nidn)
            i.putExtra("nama", listItem.nmDosen)
            i.putExtra("jabatan", listItem.jabatan)
            i.putExtra("golpat", listItem.golonganpangkat)
            i.putExtra("pendidikan", listItem.pendidikan)
            i.putExtra("keahlian", listItem.keahlian)
            i.putExtra("studi", listItem.programstudi)
            context.startActivity(i)
        }
        holder.btnHapus!!.setOnClickListener {
            val db = Campuss(context)
            val alb = AlertDialog.Builder(context)
            val nidn = holder.tvnidn!!.text
            val nama = holder.tvNmDosen!!.text
            val studi = holder.tvStudi!!.text

            with(alb){
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage("""
                        Apakah Anda yakin akan menghapus ini?
                                
                                $nama[$nidn][$studi]
                                """.trimIndent())
                                setPositiveButton("Ya"){ _, _ ->
                                if(db.hapus("$nidn"))
                        Toast.makeText(
                            context,
                            "Data mata kuliah berhasil dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        Toast.makeText(
                            context,
                            "Data mata kuliah gagal dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                setNegativeButton("Tidak", null)
                create().show()
            }
        }

        return listLayout!!
    }

    class ViewHolder {
        internal var tvNmDosen: TextView? = null
        internal var tvnidn: TextView? = null
        internal var tvStudi:TextView? = null
        internal var btnEdit: ImageButton? = null
        internal var btnHapus: ImageButton? = null

    }
}