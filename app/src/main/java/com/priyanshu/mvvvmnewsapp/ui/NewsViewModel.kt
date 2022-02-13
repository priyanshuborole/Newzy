package com.priyanshu.mvvvmnewsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyanshu.mvvvmnewsapp.model.Article
import com.priyanshu.mvvvmnewsapp.model.NewsResponse
import com.priyanshu.mvvvmnewsapp.repository.NewsRepository
import com.priyanshu.mvvvmnewsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    private val newsRepository: NewsRepository
    ): ViewModel() {

        val breakingNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
        var breakingNewsPage = 1
        var breakingNewsResponse: NewsResponse ? = null

        val searchNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
        var searchNewsPage = 1
        var searchNewsResponse : NewsResponse ? = null

    init {
        getBreakingNews("in")
    }

     fun getBreakingNews(countryCode : String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(
            countryCode,
            breakingNewsPage
        )
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun searchNews(searchQuery : String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(
            searchQuery,
            searchNewsPage
        )
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                breakingNewsPage++
                if (breakingNewsResponse == null){
                    breakingNewsResponse = resultResponse
                }
                else{
                    val oldArticle = breakingNewsResponse?.articles
                    val newArticle = resultResponse.articles
                    oldArticle?.addAll(newArticle)
                }
                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                searchNewsPage++
                if (searchNewsResponse == null){
                    searchNewsResponse = resultResponse
                }
                else{
                    val oldArticle = searchNewsResponse?.articles
                    val newArticle = resultResponse.articles
                    oldArticle?.addAll(newArticle)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun savedArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.delete(article)
    }

}