package com.springboot.graphql.api.springbootgraphqlapi.repository;

import com.springboot.graphql.api.springbootgraphqlapi.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public class MovieRepository extends JpaRepository <Movie, String> {

}
