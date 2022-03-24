package com.springboot.graphql.api.springbootgraphqlapi.service;


import com.springboot.graphql.api.springbootgraphqlapi.entity.Movie;
import com.springboot.graphql.api.springbootgraphqlapi.repository.MovieRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieDataFetcher implements DataFetcher<Movie> {

	@Autowired
	private MovieRepository movieRepository;
	
	@Override
	public Movie get(DataFetchingEnvironment dataFetchingEnvironment) {
		String id = dataFetchingEnvironment.getArgument("id");
		return movieRepository.findById(id).orElse(null);
	}
}
