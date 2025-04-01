package com.example.demo_2.Models.DAO.Detail;

import com.example.demo_2.Models.Entities.Details;
import com.example.demo_2.Models.Entities.DetailsProducts;
import com.example.demo_2.Models.Entities.Products;
import java.util.List;

public interface IDetailDao {
    
    List<Details> findAll();
    
    Details findById(Long id);
    
    Details save(Details detail);
    
    void deleteById(Long id);
    
    Details update(Details detail);
    
    void addProductToDetail(Long detailId, Products product, int quantity);
    
    List<DetailsProducts> findProductsByDetailId(Long detailId);
}
