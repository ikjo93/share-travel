package com.sharetravel.domain.boardcategory.entity;

import com.sharetravel.global.domain.BaseTimeEntity;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import java.util.ArrayList;
import java.util.List;

import com.sharetravel.domain.board.entity.Board;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NamedEntityGraph(
    name = "BoardCategoryWithAll",
    attributeNodes = {
        @NamedAttributeNode(value = "boards", subgraph = "boards")
    },
    subgraphs = @NamedSubgraph(
        name = "boards",
        attributeNodes = {
            @NamedAttributeNode("author")
        }
    )
)
@Table(name = "board_category")
@Entity
public class BoardCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_category_id")
    private Long id;

    @Column(name = "category_name")
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Board> boards = new ArrayList<>();
}
