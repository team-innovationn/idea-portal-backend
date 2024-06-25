//package com.ecobank.idea.dto.user;
//
//import com.ecobank.idea.entity.Role;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//import org.springframework.data.domain.Page;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Getter
//@Setter
//@ToString
//@RequiredArgsConstructor
//public class UserPagedFetchResponseDTO<User> {
//    private Role role;
//
//    private List<User> content;
//    private int pageNumber;
//    private int pageSize;
//    private long totalElements;
//    private int totalPages;
//    private boolean last;
//    private
//
//    public UserPagedFetchResponseDTO(Page<User> page, Role role) {
//        List<User> content = page.getContent();
//        content.removeAll(new ArrayList<>(Arrays.asList("apple", "banana", "cherry")));
//        this.content = content;
//        this.pageNumber = page.getNumber();
//        this.pageSize = page.getSize();
//        this.totalElements = page.getTotalElements();
//        this.totalPages = page.getTotalPages();
//        this.last = page.isLast();
//    }
//
//    private void some() {
//        content.remove("department");
//    }
//}
