package com.company.gamestore.ServiceLayer;

import com.company.gamestore.Models.*;
import com.company.gamestore.Repositories.*;
import com.company.gamestore.ViewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;
import java.math.BigDecimal;
import java.util.Optional;

@Component
public class InvoiceService {
    @Autowired
    private TaxRepository taxRepo;
    private FeeRepository feeRepo;
    private GameRepository gameRepo;
    private TShirtRepository tshirtRepo;
    private ConsoleRepository consoleRepo;
    private InvoiceRepository invoiceRepo;


    @Autowired
    public InvoiceService(TaxRepository taxRepo, FeeRepository feeRepo, GameRepository gameRepo, TShirtRepository tshirtRepo, ConsoleRepository consoleRepo, InvoiceRepository invoiceRepo) {
        this.taxRepo = taxRepo;
        this.feeRepo = feeRepo;
        this.gameRepo = gameRepo;
        this.tshirtRepo = tshirtRepo;
        this.consoleRepo = consoleRepo;
        this.invoiceRepo = invoiceRepo;
    }

    public Invoice saveInvoice(InvoiceViewModel invoiceViewModel){
        BigDecimal unitPrice;
        int itemId = invoiceViewModel.getItem_id();
        int quantity = invoiceViewModel.getQuantity();
        String itemType = invoiceViewModel.getItem_type();
        Optional<Tax> tax = taxRepo.findByState(invoiceViewModel.getState());

        // check if the order contains a valid state code.
        if (tax.isPresent()){
            // calculate unit price
            switch(itemType){
                case "Game":
                    // make sure we have item id, if don't have item then say no item type with this id (not found exception)
                    Optional<Game> game = gameRepo.findById(itemId);
                    Game updatedGame;
                    if (game.isPresent()){
                        updatedGame = game.get();
                        unitPrice = updatedGame.getPrice();
                        // order quantity must be greater than zero (illegal argument exception)
                        if(quantity > 0) {
                            // order quantity must be less than or equal to the number of items available in the inventory.
                            if (updatedGame.getQuantity() >= quantity) {
                                updatedGame.setQuantity(updatedGame.getQuantity() - quantity);
                                gameRepo.save(updatedGame);
                            }else throw new IllegalArgumentException("Invalid quantity - Not enough inventory");
                        }else throw new IllegalArgumentException("Quantity must be greater than 0");
                    }else throw new NotFoundException("Game ID is not valid");
                    break;
                case "T-Shirt":
                    Optional<TShirt> tshirt = tshirtRepo.findById(itemId);
                    TShirt updatedTShirt;
                    // make sure we have item id, if don't have item then say no item type with this id (not found exception)
                    if (tshirt.isPresent()){
                        updatedTShirt = tshirt.get();
                        unitPrice = updatedTShirt.getPrice();
                        // order quantity must be greater than zero (illegal argument exception)
                        if(quantity > 0) {
                            // order quantity must be less than or equal to the number of items available in the inventory.
                            if (updatedTShirt.getQuantity() >= quantity) {
                                updatedTShirt.setQuantity(updatedTShirt.getQuantity() - quantity);
                                tshirtRepo.save(updatedTShirt);
                            }else throw new IllegalArgumentException("Invalid quantity - Not enough inventory");
                        }else throw new IllegalArgumentException("Quantity must be greater than 0");
                    } else throw new NotFoundException("TShirt ID is not valid");
                    break;
                case "Console":
                    Optional<Console> console = consoleRepo.findById(itemId);
                    Console updatedConsole;
                    // make sure we have item id, if don't have item then say no item type with this id (not found exception)
                    if (console.isPresent()){
                        updatedConsole = console.get();
                        unitPrice = updatedConsole.getPrice();
                        //Order quantity must be greater than zero (illegal argument exception)
                        if(quantity > 0) {
                            //Order quantity must be less than or equal to the number of items available in the inventory.
                            if (updatedConsole.getQuantity() >= quantity) {
                                updatedConsole.setQuantity(updatedConsole.getQuantity() - quantity);
                                consoleRepo.save(updatedConsole);
                            } else throw new IllegalArgumentException("Invalid quantity - Not enough inventory");
                        } else throw new IllegalArgumentException("Quantity must be greater than 0");
                    } else throw new NotFoundException("Console ID is not valid");
                    break;
                default:
                    throw new NotFoundException("Unprocessable entity");
            }
        } else throw new NotFoundException("Invalid state: " + invoiceViewModel.getState());


        // calculate subtotal, tax, processing fee, total
        BigDecimal subtotal = unitPrice.multiply(new BigDecimal(quantity));
        //make sure state is valid, in state list, if not throw an error (optional)
        BigDecimal taxAmount = taxRepo.findByState(invoiceViewModel.getState()).get().getRate(); //maybe don't need extra get()
        BigDecimal totalTax = subtotal.multiply(taxAmount);
        BigDecimal processingFee = feeRepo.findByProductType(itemType).get().getFee();
        if(quantity > 10) {
            processingFee = processingFee.add(new BigDecimal("15.49"));
        }
        BigDecimal total = subtotal.add(totalTax).add(processingFee); //double check formatting (set scale) to 2 for 2 decimals, round half up

        //max total can be 999.99 at most, if total is greater than throw an error saying it should be under 1k
        if(total.compareTo(new BigDecimal(999.99))>0) throw new IllegalArgumentException("Total must be less than $1,000");

        // create invoice
        Invoice invoice = new Invoice();
        invoice.setName(invoiceViewModel.getName());
        invoice.setStreet(invoiceViewModel.getStreet());
        invoice.setCity(invoiceViewModel.getCity());
        invoice.setState(invoiceViewModel.getState());
        invoice.setZipcode(invoiceViewModel.getZipcode());
        invoice.setItem_type(itemType);
        invoice.setItem_id(itemId);
        invoice.setUnit_price(unitPrice.setScale(2));
        invoice.setQuantity(quantity);
        invoice.setSubtotal(subtotal.setScale(2));
        invoice.setTax(totalTax.setScale(2));
        invoice.setProcessing_fee(processingFee.setScale(2));
        invoice.setTotal(total.setScale(2));

        invoice = invoiceRepo.save(invoice);
        return invoice;
    }
}
