package com.sample.qfxl.sampleapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sample.qfxl.sampleapp.R
import com.sample.qfxl.sampleapp.room.User

/**
 * <pre>
 *     author : qfxl
 *     e-mail : xuyonghong0822@gmail.com
 *     time   : 2018/11/08
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class UserAdapter : RecyclerView.Adapter<UserViewHolder>() {

    private var userList: List<User>? = null

    private var onItemClickListener: OnItemTapListener? = null

    fun setUserList(list: List<User>?) {
        userList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(itemView)
    }

    override fun getItemCount(): Int = if (userList != null) userList!!.size else 0

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.nameTv.text = "姓名：${userList?.get(position)?.name}"
        holder.ageTv.text = "年龄：${userList?.get(position)?.age.toString()}"

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(it, position)
        }

        holder.itemView.setOnLongClickListener {
            if (onItemClickListener != null) onItemClickListener!!.onItemLongClick(it, position) else false
        }
    }

    fun setOnTapClickListener(action: (view: View, position: Int) -> Unit, longAction: (view: View, position: Int) -> Boolean) {
        onItemClickListener = object : OnItemTapListener {
            override fun onItemClick(view: View, position: Int) {
                action(view, position)
            }

            override fun onItemLongClick(view: View, position: Int): Boolean = longAction(view, position)
        }
    }

    interface OnItemTapListener {
        /**
         * onItemClick
         */
        fun onItemClick(view: View, position: Int)

        /**
         * onItemLongClick
         */
        fun onItemLongClick(view: View, position: Int): Boolean
    }
}

class UserViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
    init {
        view.setOnCreateContextMenuListener(this)
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        menu.add(0, v.id, 0, "删除")
    }

    val nameTv = view.findViewById<TextView>(R.id.tv_user_item_name)
    val ageTv = view.findViewById<TextView>(R.id.tv_user_item_age)
}