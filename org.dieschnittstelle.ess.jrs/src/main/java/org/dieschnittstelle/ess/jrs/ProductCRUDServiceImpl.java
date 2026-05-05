package org.dieschnittstelle.ess.jrs;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.GenericCRUDExecutor;
import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;

import java.util.List;

import static org.dieschnittstelle.ess.jrs.TouchpointCRUDServiceImpl.logger;

/*
 * TODO JRS2: implementieren Sie hier die im Interface deklarierten Methoden
 */

public class ProductCRUDServiceImpl implements IProductCRUDService {
	protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(ProductCRUDServiceImpl.class);
	private GenericCRUDExecutor<IndividualisedProductItem> productCRUD;

	public ProductCRUDServiceImpl(@Context ServletContext servletContext, @Context HttpServletRequest request) {
		logger.info("<constructor>: " + servletContext + "/" + request);
		this.productCRUD = (GenericCRUDExecutor<IndividualisedProductItem>) servletContext.getAttribute("productCRUD");
		logger.debug("read out the productCRUD from the servlet context: " + this.productCRUD);
	}

	@Override
	public IndividualisedProductItem createProduct(
			IndividualisedProductItem prod) {
		return (IndividualisedProductItem) this.productCRUD.createObject(prod);
	}

	@Override
	public List<IndividualisedProductItem> readAllProducts() {
		return (List) this.productCRUD.readAllObjects();
	}

	@Override
	public IndividualisedProductItem updateProduct(long id,
			IndividualisedProductItem update) {
		update.setId(id);
		return (IndividualisedProductItem) this.productCRUD.updateObject(update);
	}

	@Override
	public boolean deleteProduct(long id) {
		IndividualisedProductItem productToDelete = this.productCRUD.readObject(id);
		if (productToDelete == null){
			throw new NotFoundException("Product to delete was not found");
		}
		return this.productCRUD.deleteObject(id);
	}

	@Override
	public IndividualisedProductItem readProduct(long id) {
		IndividualisedProductItem product =  this.productCRUD.readObject(id);
		if (product == null) {
			throw new NotFoundException("Product with id: " + id + " was not found");
		}
		return product;
	}
	
}
