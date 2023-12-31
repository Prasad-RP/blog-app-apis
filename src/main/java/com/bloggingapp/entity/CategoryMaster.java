package com.bloggingapp.entity;
/**
 * @author Prasad Pansare
 */
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@Setter
public class CategoryMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int categotyId;
	
	@Column(name = "title", nullable = false)
	private String categoryTitle;
	
	@Column(name = "descrption")
	private String categoryDescription;
	
	@OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
	private List<PostMaster> post;
}
