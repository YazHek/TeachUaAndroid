package com.softserve.teachUaApp.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.softserve.teachUaApp.ClubInfoActivity
import com.softserve.teachUaApp.models.ClubModel
import com.softserve.teachUaApp.R
import com.softserve.teachUaApp.dto.global.ClubDescriptionText
import com.softserve.teachUaApp.json.GsonDeserializer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_item.view.*


class RCVAdapter(context: Context) :
    PagingDataAdapter<ClubModel, RCVAdapter.ClubsViewHolder>(ClubDiffItemCallback),
    OnClickListener {


    private val picasso = Picasso.get()
    private val base = "https://speak-ukrainian.org.ua/dev/"
    lateinit var con: Context

    val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubsViewHolder {

        con = parent.context
        return ClubsViewHolder(layoutInflater.inflate(R.layout.card_item, parent, false))
    }

    override fun onBindViewHolder(holder: ClubsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
        var desc = GsonDeserializer().deserialize(getItem(position)?.clubDescription,
            ClubDescriptionText::class.java)
        holder.itemView.setOnClickListener {
            val intent = Intent(con, ClubInfoActivity::class.java)
            intent.putExtra("clubName", getItem(position)?.clubName)
            if (desc.blocks.size == 4) {

                intent.putExtra("clubDescription", desc.blocks[3].text)
            } else
                intent.putExtra("clubDescription", desc.blocks[0].text)

            startActivity(con, intent, null)
        }
        println(getItem(position)?.clubName)
    }

    lateinit var mClickListener: ClickListener

    fun setOnClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onClick(pos: Int, aView: View)
    }


    inner class ClubsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }


        fun bind(model: ClubModel) {
            itemView.clubTitle.text = model.clubName

            val desc = GsonDeserializer().deserialize(model.clubDescription,
                ClubDescriptionText::class.java)
            if (desc.blocks.size == 4) {

                itemView.clubDescription.text = desc.blocks[3].text
            } else if (desc.blocks.size == 3) {

                itemView.clubDescription.text = desc.blocks[0].text
            } else
                itemView.clubDescription.text = desc.blocks[0].text


            println(model.clubId)
            println(desc.blocks.toString())
            if (model.clubImage.endsWith(".png")) {
                //println(model!!.clubImage.endsWith(".png"))
                picasso.load(base + model.clubImage).fit().centerCrop().into(itemView.clubLogo)
            } else if (model.clubImage.endsWith(".svg"))
                GlideToVectorYou
                    .init()
                    .with(layoutInflater.context)
                    .setPlaceHolder(
                        R.drawable.ic_launcher_background,
                        R.drawable.ic_launcher_background
                    )

                    .load((base + model.clubImage).toUri(), itemView.clubLogo)
            println(model.clubImage)

            itemView.clubCategory.text = model.clubCategoryName
            println(model.clubCategoryName)
            itemView.clubCategory.setBackgroundColor(Color.parseColor(model.clubBackgroundColor))
            itemView.clubLogo.setBackgroundColor(Color.parseColor(model.clubBackgroundColor))

        }


        override fun onClick(p0: View?) {
            mClickListener.onClick(absoluteAdapterPosition, itemView)
        }

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }


}

private object ClubDiffItemCallback : DiffUtil.ItemCallback<ClubModel>() {
    override fun areItemsTheSame(oldItem: ClubModel, newItem: ClubModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ClubModel, newItem: ClubModel): Boolean {
        return oldItem.clubId == newItem.clubId
    }


}

