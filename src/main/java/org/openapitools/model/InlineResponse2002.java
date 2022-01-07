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
 * InlineResponse2002
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-07T13:55:52.047472600+01:00[Europe/Madrid]")
public class InlineResponse2002   {
  @JsonProperty("reference")
  private String reference;

  @JsonProperty("status")
  private String status;

  @JsonProperty("amount")
  private Float amount;

  @JsonProperty("fee")
  private Float fee;

  public InlineResponse2002 reference(String reference) {
    this.reference = reference;
    return this;
  }

  /**
   * The transaction reference number
   * @return reference
  */
  @ApiModelProperty(value = "The transaction reference number")


  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public InlineResponse2002 status(String status) {
    this.status = status;
    return this;
  }

  /**
   * The status of the transaction. It can be any of these values PENDING, SETTLED, FUTURE, INVALID
   * @return status
  */
  @ApiModelProperty(value = "The status of the transaction. It can be any of these values PENDING, SETTLED, FUTURE, INVALID")


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public InlineResponse2002 amount(Float amount) {
    this.amount = amount;
    return this;
  }

  /**
   * the amount of the transaction
   * @return amount
  */
  @ApiModelProperty(value = "the amount of the transaction")


  public Float getAmount() {
    return amount;
  }

  public void setAmount(Float amount) {
    this.amount = amount;
  }

  public InlineResponse2002 fee(Float fee) {
    this.fee = fee;
    return this;
  }

  /**
   * The fee applied to the transaction
   * @return fee
  */
  @ApiModelProperty(value = "The fee applied to the transaction")


  public Float getFee() {
    return fee;
  }

  public void setFee(Float fee) {
    this.fee = fee;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse2002 inlineResponse2002 = (InlineResponse2002) o;
    return Objects.equals(this.reference, inlineResponse2002.reference) &&
        Objects.equals(this.status, inlineResponse2002.status) &&
        Objects.equals(this.amount, inlineResponse2002.amount) &&
        Objects.equals(this.fee, inlineResponse2002.fee);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reference, status, amount, fee);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2002 {\n");
    
    sb.append("    reference: ").append(toIndentedString(reference)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    fee: ").append(toIndentedString(fee)).append("\n");
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

