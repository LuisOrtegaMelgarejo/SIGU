package com.luisortega.myapplication

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ViewFlipper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.formatter.PercentFormatter
import android.util.Log
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnChartValueSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Puede ser que enviemos el reporte a su correo", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        nav_view.getMenu().getItem(0).setChecked(true)

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.inicio-> {
                findViewById<ViewFlipper>(R.id.vf).displayedChild=0
            }
            R.id.reporte_gastos -> {
                val pieChart = findViewById<View>(R.id.piechart) as PieChart
                pieChart.setUsePercentValues(true)
                pieChart.description=Description()
                pieChart.holeRadius=58f

                val yvalues = ArrayList<PieEntry>()

                yvalues.add(PieEntry(8f, "January"))
                yvalues.add(PieEntry(15f, "February"))
                yvalues.add(PieEntry(12f, "March"))
                yvalues.add(PieEntry(25f, "April"))
                yvalues.add(PieEntry(23f, "May"))
                yvalues.add(PieEntry(17f, "June"))

                val dataSet = PieDataSet(yvalues, "Election Results")
                val data = PieData(dataSet)

                data.setValueFormatter(PercentFormatter())
                pieChart.data=data

                findViewById<ViewFlipper>(R.id.vf).displayedChild=1
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

     override fun onValueSelected(e: Entry, h: Highlight) {
        if (e == null)
            return
        Log.i("VAL SELECTED", "data: " + e.data + ", xIndex: " + e.x )
     }

    override fun onNothingSelected() {
        Log.i("PieChart", "nothing selected")
    }
}

