package mobile.event_planner_app.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mobile.event_planner_app.R



class ItemFragment : Fragment() {

    private lateinit var items: ArrayList<String>
    private lateinit var rcList: RecyclerView
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = Firebase.database.reference

        items = arrayListOf()


/*
        for (i in 0..10){
            items.add("item $i")


        }*/


        database.child("items").get().addOnSuccessListener {
            if (it.value != null){
                val itemsFromDB = it.value as HashMap<String, Any>
                items.clear()
                itemsFromDB.map {(key,value) ->


                    val itemFromDb = value as HashMap<String,Any>

                    val name = itemFromDb.get("name").toString()
                    items.add(name)
                }
                rcList.adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)




        rcList = view.findViewById(R.id.list)
        rcList.layoutManager = LinearLayoutManager(context)
        rcList.adapter = MyListRecyclerViewAdapter(items)


        return view
    }

}