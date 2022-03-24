package com.springboot.graphql.api.springbootgraphqlapi.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class Movie {

	@Id
	private String id;
	private String title;
	private String[] directors;
	private String[] actors;
	private String releaseDate;
}
