package xyz.dokup.stickyheadersample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private val itemAdapter by lazy { ItemAdapter(this, items) }

    private val items = ArrayList<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = itemAdapter
        recyclerView.addItemDecoration(StickyLabelItemDecoration(this, object : StickyLabelItemDecoration.Callback{
            override fun getInitial(position: Int): String {
                return items[position].name[0].toString()
            }
        }))

        createItemList()
    }

    private fun createItemList() {
        for(i in 1 .. 5) {
            items.add(Item("AAA"))
        }
        for(i in 1 .. 5) {
            items.add(Item("BBB"))
        }
        for(i in 1 .. 5) {
            items.add(Item("CCC"))
        }
        for(i in 1 .. 5) {
            items.add(Item("DDD"))
        }
        for(i in 1 .. 5) {
            items.add(Item("EEE"))
        }


        itemAdapter.notifyDataSetChanged()
    }


}
