package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * InlineResponse200Transactions
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-08T18:39:14.364924800+01:00[Europe/Madrid]")
public class InlineResponse200Transactions   {
  @JsonProperty("reference")
  private String reference;

  @JsonProperty("account_iban")
  private String accountIban;

  @JsonProperty("date")
  private String date;

  @JsonProperty("amount")
  private Float amount;

  @JsonProperty("fee")
  private Float fee;

  @JsonProperty("description")
  private String description;

  public InlineResponse200Transactions reference(String reference) {
    this.reference = reference;
    return this;
  }

  /**
   * The transaction unique reference number in our system. If not present, the system will generate one.
   * @return reference
  */
  @ApiModelProperty(value = "The transaction unique reference number in our system. If not present, the system will generate one.")


  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public InlineResponse200Transactions accountIban(String accountIban) {
    this.accountIban = accountIban;
    return this;
  }

  /**
   * The IBAN number of the account where the transaction has happened.
   * @return accountIban
  */
  @ApiModelProperty(value = "The IBAN number of the account where the transaction has happened.")


  public String getAccountIban() {
    return accountIban;
  }

  public void setAccountIban(String accountIban) {
    this.accountIban = accountIban;
  }

  public InlineResponse200Transactions date(String date) {
    this.date = date;
    return this;
  }

  /**
   * Date when the transaction took place
   * @return date
  */
  @ApiModelProperty(value = "Date when the transaction took place")


  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public InlineResponse200Transactions amount(Float amount) {
    this.amount = amount;
    return this;
  }

  /**
   * If positive the transaction is a credit (add money) to the account. If negative it is a debit (deduct money from the account)
   * @return amount
  */
  @ApiModelProperty(value = "If positive the transaction is a credit (add money) to the account. If negative it is a debit (deduct money from the account)")


  public Float getAmount() {
    return amount;
  }

  public void setAmount(Float amount) {
    this.amount = amount;
  }

  public InlineResponse200Transactions fee(Float fee) {
    this.fee = fee;
    return this;
  }

  /**
   * Fee that will be deducted from the amount, regardless on the amount being positive or negative.
   * @return fee
  */
  @ApiModelProperty(value = "Fee that will be deducted from the amount, regardless on the amount being positive or negative.")


  public Float getFee() {
    return fee;
  }

  public void setFee(Float fee) {
    this.fee = fee;
  }

  public InlineResponse200Transactions description(String description) {
    this.description = description;
    return this;
  }

  /**
   * The description of the transaction
   * @return description
  */
  @ApiModelProperty(value = "The description of the transaction")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse200Transactions inlineResponse200Transactions = (InlineResponse200Transactions) o;
    return Objects.equals(this.reference, inlineResponse200Transactions.reference) &&
        Objects.equals(this.accountIban, inlineResponse200Transactions.accountIban) &&
        Objects.equals(this.date, inlineResponse200Transactions.date) &&
        Objects.equals(this.amount, inlineResponse200Transactions.amount) &&
        Objects.equals(this.fee, inlineResponse200Transactions.fee) &&
        Objects.equals(this.description, inlineResponse200Transactions.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reference, accountIban, date, amount, fee, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse200Transactions {\n");
    
    sb.append("    reference: ").append(toIndentedString(reference)).append("\n");
    sb.append("    accountIban: ").append(toIndentedString(accountIban)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    fee: ").append(toIndentedString(fee)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

