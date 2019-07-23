package com.example.kotlinrss

import android.content.Intent
import android.net.Uri
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row.view.*

private var WebUrl:String = "";

class MainAdapter (val items:Items) : RecyclerView.Adapter<MainViewHolder>(){
    override fun getItemCount(): Int {
        return items.items.size

    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val feedItems = items.items[position]

        holder.itemView.textView_title.apply { text = feedItems.title}
        holder.itemView.textView_date.apply { text = feedItems.pubDate }

        val thumbnail = holder.itemView.imageView
        if(feedItems.thumbnail != ""){
            Picasso.get().load(feedItems.thumbnail).into(thumbnail)
        }
        else{thumbnail.setImageResource(android.R.color.transparent)}

        var categoryTxt:String = ""
        for (i in feedItems.categories){
            var html:String = HtmlCompat.fromHtml(i, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()

            categoryTxt += (html + ", ")
        }



        val htmltxt = HtmlCompat.fromHtml(feedItems.description, HtmlCompat.FROM_HTML_MODE_COMPACT)

        holder.itemView.textView_categories.apply { text =  categoryTxt}
        holder.itemView.textView_desc.apply { text = htmltxt }

        holder.itemView.setOnClickListener {
            println(feedItems.link)
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(feedItems.link))
            holder.itemView.context.startActivity(browserIntent)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val feed = inflater.inflate(R.layout.item_row, parent, false)
        return MainViewHolder(feed)
    }



}

class MainViewHolder(view:View) : RecyclerView.ViewHolder(view){
    /*init {
        view.setOnClickListener {
            //val intent = Intent(view.context, CourseDetailActivity::class.java)
            println(WebUrl)
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(WebUrl))
            view.context.startActivity(browserIntent)

        }
    }*/
}