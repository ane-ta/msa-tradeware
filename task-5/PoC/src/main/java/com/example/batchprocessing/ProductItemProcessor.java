package com.example.batchprocessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class ProductItemProcessor implements ItemProcessor<Product, Product> {

	private static final Logger log = LoggerFactory.getLogger(ProductItemProcessor.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

    @Override
	public Product process(final Product product) {
		final Long productId = product.productId();
		final Long productSku = product.productSku();
		final String productName = product.productName();
		final Long productAmount = product.productAmount();
		final String productData = product.productData();

		List<Loyality> results = jdbcTemplate.query(
			"SELECT * FROM loyality_data WHERE productSku = ?", 
			new DataClassRowMapper<>(Loyality.class), 
			productSku
		);

		final String finalLoyalty = results.isEmpty() 
			? productData 
			: results.get(0).loyalityData();
		// 3. Возвращаем итоговый объект
		final Product transformedProduct = new Product(productId, productSku, productName, productAmount, finalLoyalty);

		log.info("Transforming ({}) into ({})", product, transformedProduct);

		return transformedProduct;
	}
}
