/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import java.math.BigDecimal;
import java.util.Date;
import static org.hamcrest.core.Is.is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.when;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.invoicing.BookKeeper;
import pl.com.bottega.ecommerce.sales.domain.invoicing.Invoice;
import pl.com.bottega.ecommerce.sales.domain.invoicing.InvoiceFactory;
import pl.com.bottega.ecommerce.sales.domain.invoicing.InvoiceRequest;
import pl.com.bottega.ecommerce.sales.domain.invoicing.RequestItem;
import pl.com.bottega.ecommerce.sales.domain.invoicing.Tax;
import pl.com.bottega.ecommerce.sales.domain.invoicing.TaxPolicy;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

/**
 *
 * @author jankowskirobert
 */
public class TestInvoice {

    private BookKeeper bookKeeper;
    private TaxPolicy policy;
    private Invoice inv;
    private InvoiceFactory factory;
    private final InvoiceRequest request = new InvoiceRequest(new ClientData(Id.generate(), "Tester"));
    private final ProductData product = new ProductData(Id.generate(), new Money(100), "test", ProductType.FOOD, new Date());
    private final RequestItem item = new RequestItem(product, 10, new Money(100));

    @Before
    public void setUp() {
        policy = mock(TaxPolicy.class);
        factory = mock(InvoiceFactory.class);
        bookKeeper = new BookKeeper(factory);        
    }

    @Test
    public void testInvoiceShouldContainOneItem() {
        request.add(item);
        when(policy.calculateTax(ProductType.FOOD, item.getTotalCost())).thenReturn(new Tax(new Money(new BigDecimal(10)), "Def"));
        inv = bookKeeper.issuance(request, policy);
        Assert.assertThat(inv.getItems().size(), is(1));
    }
    @Test
    public void testInvoiceShouldCallTaxCalculationTwice() {
        request.add(item);
        when(policy.calculateTax(ProductType.FOOD, item.getTotalCost())).thenReturn(new Tax(new Money(new BigDecimal(10)), "Def"));
        inv = bookKeeper.issuance(request, policy);
        Assert.assertThat(inv.getItems().size(), is(1));
    }
}
