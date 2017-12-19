package re.spitfy.ctftime.adapters

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import re.spitfy.ctftime.R
import re.spitfy.ctftime.data.Ranking
import re.spitfy.ctftime.viewHolder.RankingProgressBarViewHolder
import re.spitfy.ctftime.viewHolder.TeamRankViewHolder

class RankingsFirestoreAdapter(private val rankingsList: List<Ranking?>, recyclerView : RecyclerView?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private val ITEM_VIEW = 0
        private val LOADING_VIEW = 1
    }
    lateinit var onLoadMoreListener : OnLoadMoreListener
    private var visibleThreshold = 20
    private var lastVisibleItem = 0
    private var totalItemCount = 0
    var isLoading = true
    var isAllLoaded = false

    init {
        if (recyclerView?.layoutManager is LinearLayoutManager) {
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val llm = recyclerView?.layoutManager as LinearLayoutManager
                    lastVisibleItem = llm.findLastVisibleItemPosition()
                    totalItemCount = llm.itemCount

                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)
                            && !isAllLoaded) {
                        onLoadMoreListener.onLoadMore()
                    }
                    isLoading = true

                }
            })
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (rankingsList.getOrNull(position) != null) ITEM_VIEW else LOADING_VIEW
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val viewType = holder?.itemViewType
        val itemModel = rankingsList[position]

        if (viewType == ITEM_VIEW && holder is TeamRankViewHolder) {
            holder.bind(itemModel)
        } else if (viewType == LOADING_VIEW && holder is RankingProgressBarViewHolder) {
            if(isLoading && !isAllLoaded) {
                holder.progressBar.visibility = View.VISIBLE
            } else if (!isLoading ||  isAllLoaded) {
                holder.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TeamRankViewHolder? {
        if (viewType == ITEM_VIEW) {
            val v = LayoutInflater
                    .from(parent?.context)
                    .inflate(R.layout.team_rankings_row,
                            parent,
                            false)
            return TeamRankViewHolder(v, parent)
        } else if (viewType == LOADING_VIEW){
            val v = LayoutInflater
                    .from(parent?.context)
                    .inflate(R.layout.rankings_progress_bar,
                            parent,
                            false)
            return TeamRankViewHolder(v, parent)
        }
        return null
    }

    override fun getItemCount(): Int {
        return rankingsList.size
    }
}