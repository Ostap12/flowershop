package ua.galicia.flowershop.dao.impl;
 
import java.util.Date;
 
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ua.galicia.flowershop.dao.ProductDAO;
import ua.galicia.flowershop.entity.Flower;
import ua.galicia.flowershop.model.PaginationResult;
import ua.galicia.flowershop.model.FlowerInfo;

// Transactional for Hibernate
@Transactional
public class ProductDAOImpl implements ProductDAO {
 
    @Autowired
    private SessionFactory sessionFactory;
 
    @Override
    public Flower findFlower(String code) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Flower.class);
        crit.add(Restrictions.eq("code", code));
        return (Flower) crit.uniqueResult();
    }
 
    @Override
    public FlowerInfo findFlowerInfo(String code) {
        Flower product = this.findFlower(code);
        if (product == null) {
            return null;
        }
        return new FlowerInfo(product.getCode(), product.getName(), product.getPrice());
    }
 
    @Override
    public void save(FlowerInfo flowerInfo) {
        String code = flowerInfo.getCode();
 
        Flower flower = null;
 
        boolean isNew = false;
        if (code != null) {
        	flower = this.findFlower(code);
        }
        if (flower == null) {
            isNew = true;
            flower = new Flower();
            flower.setCreateDate(new Date());
        }
        flower.setCode(code);
        flower.setName(flowerInfo.getName());
        flower.setPrice(flowerInfo.getPrice());
 
        if (flowerInfo.getFileData() != null) {
            byte[] image = flowerInfo.getFileData().getBytes();
            if (image != null && image.length > 0) {
            	flower.setImage(image);
            }
        }
        if (isNew) {
            this.sessionFactory.getCurrentSession().persist(flower);
        }
        // If error in DB, Exceptions will be thrown out immediately
        this.sessionFactory.getCurrentSession().flush();
    }
 
    @Override
    public PaginationResult<FlowerInfo> queryProducts(int page, int maxResult, int maxNavigationPage,
            String likeName) {
        String sql = "Select new " + FlowerInfo.class.getName() //
                + "(p.code, p.name, p.price) " + " from "//
                + Flower.class.getName() + " p ";
        if (likeName != null && likeName.length() > 0) {
            sql += " Where lower(p.name) like :likeName ";
        }
        sql += " order by p.createDate desc ";
        //
        Session session = sessionFactory.getCurrentSession();
 
        Query query = session.createQuery(sql);
        if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
        return new PaginationResult<FlowerInfo>(query, page, maxResult, maxNavigationPage);
    }
 
    @Override
    public PaginationResult<FlowerInfo> queryProducts(int page, int maxResult, int maxNavigationPage) {
        return queryProducts(page, maxResult, maxNavigationPage, null);
    }
    
}