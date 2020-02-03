package br.ufrgs.inf.pet.dinoapp.activity.menu_itens.glossary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import br.ufrgs.inf.pet.dinoapp.R
import br.ufrgs.inf.pet.dinoapp.database.glossary_item.GlossaryItem


class GlossaryListAdapter(context: Context, private val dataSource: List<GlossaryItem?>) : BaseAdapter(), Filterable {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val filter = ItemFilter()

    private var filteredDataSource = dataSource

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {

            view = inflater.inflate(R.layout.glossary_item, null)

            holder = ViewHolder()
            holder.tvTitle = view.findViewById(R.id.glossary_item_tv_title) as TextView
            holder.tvText = view.findViewById(R.id.glossary_item_tv_text) as TextView

            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val glossaryItem = getItem(position) as GlossaryItem

        if (glossaryItem != null) {
            holder.tvTitle.text = glossaryItem.title

            if (!glossaryItem.text.isNullOrEmpty()) {
                holder.tvText.text = glossaryItem.text
            }
        }

        return view
    }

    override fun getFilter(): Filter {
        return filter
    }

    override fun getCount(): Int {
        return filteredDataSource.size
    }

    override fun getItem(position: Int): Any {
        return filteredDataSource[position]!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private class ViewHolder {
        lateinit var tvTitle: TextView
        lateinit var tvText: TextView
    }

    inner class ItemFilter : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterString = constraint.toString().toLowerCase()

            val results = FilterResults()

            val list: List<GlossaryItem?> = dataSource

            val count = list.size
            val nList = ArrayList<GlossaryItem?>(count)

            var filterableString: String

            for (i in 0 until count) {
                val glossaryItem = dataSource[i]
                if (glossaryItem != null) {
                    if (glossaryItem.title!!.toLowerCase().contains(filterString)
                        || (!glossaryItem.text.isNullOrEmpty() && glossaryItem.text!!.toLowerCase().contains(filterString))) {
                        nList.add(glossaryItem)
                    }
                }
            }

            results.values = nList
            results.count = nList.size

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredDataSource = results!!.values as ArrayList<GlossaryItem?>
            notifyDataSetChanged()
        }
    }
}