package com.priyanshu.mvvvmnewsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.priyanshu.mvvvmnewsapp.R
import com.priyanshu.mvvvmnewsapp.model.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article_preview,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(holder.articleImage)
            holder.articleSource.text = article.source?.name
            holder.articleDesc.text = article.description
            holder.articleTitle.text = article.title
            holder.articlePublish.text = article.publishedAt
            setOnClickListener{
                onItemClickListener?.let { it(article) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener : (Article)-> Unit){
        onItemClickListener = listener
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val articleImage: ImageView = itemView.findViewById(R.id.ivArticleImage)
        val articleSource: TextView = itemView.findViewById(R.id.tvSource)
        val articleDesc: TextView = itemView.findViewById(R.id.tvDescription)
        val articlePublish: TextView = itemView.findViewById(R.id.tvPublishedAt)
        val articleTitle: TextView = itemView.findViewById(R.id.tvTitle)
    }

    private val differCallback = object: DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)
}