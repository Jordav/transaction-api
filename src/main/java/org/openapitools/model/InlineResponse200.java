package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.model.InlineResponse200Transactions;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * InlineResponse200
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-08T18:39:14.364924800+01:00[Europe/Madrid]")
public class InlineResponse200   {
  @JsonProperty("transactions")
  @Valid
  private List<InlineResponse200Transactions> transactions = new ArrayList<>();

  public InlineResponse200 transactions(List<InlineResponse200Transactions> transactions) {
    this.transactions = transactions;
    return this;
  }

  public InlineResponse200 addTransactionsItem(InlineResponse200Transactions transactionsItem) {
    this.transactions.add(transactionsItem);
    return this;
  }

  /**
   * Transactions
   * @return transactions
  */
  @ApiModelProperty(required = true, value = "Transactions")
  @NotNull

  @Valid

  public List<InlineResponse200Transactions> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<InlineResponse200Transactions> transactions) {
    this.transactions = transactions;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse200 inlineResponse200 = (InlineResponse200) o;
    return Objects.equals(this.transactions, inlineResponse200.transactions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse200 {\n");
    
    sb.append("    transactions: ").append(toIndentedString(transactions)).append("\n");
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

