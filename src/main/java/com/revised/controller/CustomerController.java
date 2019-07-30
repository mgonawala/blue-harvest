package com.revised.controller;

import com.revised.dto.CustomerDto;
import com.revised.model.Customer;
import com.revised.service.ICustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that performs operations on customer resource.
 *
 * <p>It provides simple CRUD operations on Customer Resource. It is capable of producing result in
 * JSON format.
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
@RestController
@RequestMapping(
    value = "/api/v1/customers",
    produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(value = "Operations pertaining to Customers in Banking.")
public class CustomerController {

  /** Dependencies * */

  /** Logger * */
  private static final Logger logger = LogManager.getLogger(CustomerController.class);

  /** ModelMapper used to map DTO objects to Entity * */
  @Autowired private ModelMapper modelMapper;

  /** Account service carrying business login * */
  @Autowired private ICustomerService customerService;

  /**
   * Finds all available customers.
   *
   * @return Returns List of customers in response body.
   */
  @GetMapping
  @ApiOperation(value = "Operation to find all the customers of Bank.")
  public ResponseEntity<List<Customer>> findAllCustomers() {
    logger.info("Get all customers' details.");
    return new ResponseEntity<>(customerService.findAllCustomers(), HttpStatus.OK);
  }

  /**
   * Finds customer details of given customer id.
   *
   * @param id Customer Id.
   * @return Returns Customer details object.
   * @throws com.revised.exception.ResourceNotFoundException if customer is not valid.
   */
  @ApiOperation(value = "Operation to find a customer by it's id.")
  @GetMapping("/{id}")
  public ResponseEntity<Customer> findCustomerById(@Valid @Min(value = 1) @PathVariable Long id) {
    logger.info("Find details of customer:{}", id);
    return new ResponseEntity<>(customerService.findCustomerById(id), HttpStatus.OK);
  }

  /**
   * Allows to register a new customer in the system.
   *
   * @param customer Customer object containing details of customer.
   * @return Returns new created customer object.
   */
  @PostMapping
  @ApiOperation(value = "Operation to create a new customer.")
  public ResponseEntity<Customer> createNewCustomer(@Valid @RequestBody CustomerDto customer) {
    logger.info("Request to register a new customer.");
    logger.debug("Customer Request:{}", customer.toString());
    return new ResponseEntity<>(
        customerService.createNewCustomer(modelMapper.map(customer, Customer.class)),
        HttpStatus.CREATED);
  }

  /**
   * Allows to update customer details.
   *
   * @param customer Customer details to be updated.
   * @param id Customer Id.
   * @return Returns new updated customer object.
   */
  @PutMapping("/{id}")
  @ApiOperation(value = "Operation to update customer information.")
  public ResponseEntity<Customer> updateCustomer(
      @RequestBody @Valid CustomerDto customer,
      BindingResult bindingResult,
      @PathVariable Long id) {
    logger.info("Update details of customer:{}", id);
    logger.debug("Customer request:" + customer.toString());
    return new ResponseEntity<>(
        customerService.updateCustomer(modelMapper.map(customer, Customer.class), id),
        HttpStatus.OK);
  }

  /**
   * Allows to delete a customer from the system.
   *
   * @param id Customer Id.
   * @return Returns HttpStatus.NO_CONTENT status code.No response body.
   */
  @DeleteMapping("/{id}")
  @ApiOperation(value = "operation to delete a customer.")
  public ResponseEntity deleteCustomer(@Valid @Min(value = 1) @PathVariable Long id) {
    logger.info("Delete customer:{}", id);
    customerService.deleteCustomer(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
