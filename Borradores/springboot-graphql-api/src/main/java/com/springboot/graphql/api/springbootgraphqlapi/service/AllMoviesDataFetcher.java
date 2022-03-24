package com.springboot.graphql.api.springbootgraphqlapi.service;

import java.util.List;

import com.springboot.graphql.api.springbootgraphqlapi.entity.Movie;
import com.springboot.graphql.api.springbootgraphqlapi.repository.MovieRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllMoviesDataFetcher implements DataFetcher<List<Movie>> {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Override
	public List<Movie> get(DataFetchingEnvironment dataFetchingEnvironment){
		return movieRepository.findAll();
	}

}
