// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.querybuilder.test;

public final class CustomerProto {
  private CustomerProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public static final class Customer extends
      com.google.protobuf.GeneratedMessage {
    // Use Customer.newBuilder() to construct.
    private Customer() {}
    
    private static final Customer defaultInstance = new Customer();
    public static Customer getDefaultInstance() {
      return defaultInstance;
    }
    
    public Customer getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.querybuilder.test.CustomerProto.internal_static_mavenlauncher_Customer_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.querybuilder.test.CustomerProto.internal_static_mavenlauncher_Customer_fieldAccessorTable;
    }
    
    // optional int64 id = 1;
    public static final int ID_FIELD_NUMBER = 1;
    private boolean hasId;
    private long id_ = 0L;
    public boolean hasId() { return hasId; }
    public long getId() { return id_; }
    
    // optional string firstName = 2;
    public static final int FIRSTNAME_FIELD_NUMBER = 2;
    private boolean hasFirstName;
    private java.lang.String firstName_ = "";
    public boolean hasFirstName() { return hasFirstName; }
    public java.lang.String getFirstName() { return firstName_; }
    
    // optional string lastName = 3;
    public static final int LASTNAME_FIELD_NUMBER = 3;
    private boolean hasLastName;
    private java.lang.String lastName_ = "";
    public boolean hasLastName() { return hasLastName; }
    public java.lang.String getLastName() { return lastName_; }
    
    // optional string street = 4;
    public static final int STREET_FIELD_NUMBER = 4;
    private boolean hasStreet;
    private java.lang.String street_ = "";
    public boolean hasStreet() { return hasStreet; }
    public java.lang.String getStreet() { return street_; }
    
    public final boolean isInitialized() {
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (hasId()) {
        output.writeInt64(1, getId());
      }
      if (hasFirstName()) {
        output.writeString(2, getFirstName());
      }
      if (hasLastName()) {
        output.writeString(3, getLastName());
      }
      if (hasStreet()) {
        output.writeString(4, getStreet());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (hasId()) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(1, getId());
      }
      if (hasFirstName()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(2, getFirstName());
      }
      if (hasLastName()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(3, getLastName());
      }
      if (hasStreet()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(4, getStreet());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static com.querybuilder.test.CustomerProto.Customer parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.querybuilder.test.CustomerProto.Customer parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.querybuilder.test.CustomerProto.Customer parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.querybuilder.test.CustomerProto.Customer parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.querybuilder.test.CustomerProto.Customer parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.querybuilder.test.CustomerProto.Customer parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.querybuilder.test.CustomerProto.Customer parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input).buildParsed();
    }
    public static com.querybuilder.test.CustomerProto.Customer parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.querybuilder.test.CustomerProto.Customer parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.querybuilder.test.CustomerProto.Customer parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.querybuilder.test.CustomerProto.Customer prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private com.querybuilder.test.CustomerProto.Customer result;
      
      // Construct using com.querybuilder.test.CustomerProto.Customer.newBuilder()
      private Builder() {}
      
      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new com.querybuilder.test.CustomerProto.Customer();
        return builder;
      }
      
      protected com.querybuilder.test.CustomerProto.Customer internalGetResult() {
        return result;
      }
      
      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException(
            "Cannot call clear() after build().");
        }
        result = new com.querybuilder.test.CustomerProto.Customer();
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(result);
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.querybuilder.test.CustomerProto.Customer.getDescriptor();
      }
      
      public com.querybuilder.test.CustomerProto.Customer getDefaultInstanceForType() {
        return com.querybuilder.test.CustomerProto.Customer.getDefaultInstance();
      }
      
      public boolean isInitialized() {
        return result.isInitialized();
      }
      public com.querybuilder.test.CustomerProto.Customer build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }
      
      private com.querybuilder.test.CustomerProto.Customer buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public com.querybuilder.test.CustomerProto.Customer buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");
        }
        com.querybuilder.test.CustomerProto.Customer returnMe = result;
        result = null;
        return returnMe;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.querybuilder.test.CustomerProto.Customer) {
          return mergeFrom((com.querybuilder.test.CustomerProto.Customer)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(com.querybuilder.test.CustomerProto.Customer other) {
        if (other == com.querybuilder.test.CustomerProto.Customer.getDefaultInstance()) return this;
        if (other.hasId()) {
          setId(other.getId());
        }
        if (other.hasFirstName()) {
          setFirstName(other.getFirstName());
        }
        if (other.hasLastName()) {
          setLastName(other.getLastName());
        }
        if (other.hasStreet()) {
          setStreet(other.getStreet());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 8: {
              setId(input.readInt64());
              break;
            }
            case 18: {
              setFirstName(input.readString());
              break;
            }
            case 26: {
              setLastName(input.readString());
              break;
            }
            case 34: {
              setStreet(input.readString());
              break;
            }
          }
        }
      }
      
      
      // optional int64 id = 1;
      public boolean hasId() {
        return result.hasId();
      }
      public long getId() {
        return result.getId();
      }
      public Builder setId(long value) {
        result.hasId = true;
        result.id_ = value;
        return this;
      }
      public Builder clearId() {
        result.hasId = false;
        result.id_ = 0L;
        return this;
      }
      
      // optional string firstName = 2;
      public boolean hasFirstName() {
        return result.hasFirstName();
      }
      public java.lang.String getFirstName() {
        return result.getFirstName();
      }
      public Builder setFirstName(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasFirstName = true;
        result.firstName_ = value;
        return this;
      }
      public Builder clearFirstName() {
        result.hasFirstName = false;
        result.firstName_ = getDefaultInstance().getFirstName();
        return this;
      }
      
      // optional string lastName = 3;
      public boolean hasLastName() {
        return result.hasLastName();
      }
      public java.lang.String getLastName() {
        return result.getLastName();
      }
      public Builder setLastName(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasLastName = true;
        result.lastName_ = value;
        return this;
      }
      public Builder clearLastName() {
        result.hasLastName = false;
        result.lastName_ = getDefaultInstance().getLastName();
        return this;
      }
      
      // optional string street = 4;
      public boolean hasStreet() {
        return result.hasStreet();
      }
      public java.lang.String getStreet() {
        return result.getStreet();
      }
      public Builder setStreet(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasStreet = true;
        result.street_ = value;
        return this;
      }
      public Builder clearStreet() {
        result.hasStreet = false;
        result.street_ = getDefaultInstance().getStreet();
        return this;
      }
    }
    
    static {
      com.querybuilder.test.CustomerProto.getDescriptor();
    }
    
    static {
      com.querybuilder.test.CustomerProto.internalForceInit();
    }
  }
  
  public static final class CustomerList extends
      com.google.protobuf.GeneratedMessage {
    // Use CustomerList.newBuilder() to construct.
    private CustomerList() {}
    
    private static final CustomerList defaultInstance = new CustomerList();
    public static CustomerList getDefaultInstance() {
      return defaultInstance;
    }
    
    public CustomerList getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.querybuilder.test.CustomerProto.internal_static_mavenlauncher_CustomerList_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.querybuilder.test.CustomerProto.internal_static_mavenlauncher_CustomerList_fieldAccessorTable;
    }
    
    // repeated .mavenlauncher.Customer customers = 1;
    public static final int CUSTOMERS_FIELD_NUMBER = 1;
    private java.util.List<com.querybuilder.test.CustomerProto.Customer> customers_ =
      java.util.Collections.emptyList();
    public java.util.List<com.querybuilder.test.CustomerProto.Customer> getCustomersList() {
      return customers_;
    }
    public int getCustomersCount() { return customers_.size(); }
    public com.querybuilder.test.CustomerProto.Customer getCustomers(int index) {
      return customers_.get(index);
    }
    
    public final boolean isInitialized() {
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      for (com.querybuilder.test.CustomerProto.Customer element : getCustomersList()) {
        output.writeMessage(1, element);
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      for (com.querybuilder.test.CustomerProto.Customer element : getCustomersList()) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, element);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static com.querybuilder.test.CustomerProto.CustomerList parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.querybuilder.test.CustomerProto.CustomerList parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.querybuilder.test.CustomerProto.CustomerList parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.querybuilder.test.CustomerProto.CustomerList parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.querybuilder.test.CustomerProto.CustomerList parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.querybuilder.test.CustomerProto.CustomerList parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.querybuilder.test.CustomerProto.CustomerList parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input).buildParsed();
    }
    public static com.querybuilder.test.CustomerProto.CustomerList parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.querybuilder.test.CustomerProto.CustomerList parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.querybuilder.test.CustomerProto.CustomerList parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.querybuilder.test.CustomerProto.CustomerList prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private com.querybuilder.test.CustomerProto.CustomerList result;
      
      // Construct using com.querybuilder.test.CustomerProto.CustomerList.newBuilder()
      private Builder() {}
      
      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new com.querybuilder.test.CustomerProto.CustomerList();
        return builder;
      }
      
      protected com.querybuilder.test.CustomerProto.CustomerList internalGetResult() {
        return result;
      }
      
      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException(
            "Cannot call clear() after build().");
        }
        result = new com.querybuilder.test.CustomerProto.CustomerList();
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(result);
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.querybuilder.test.CustomerProto.CustomerList.getDescriptor();
      }
      
      public com.querybuilder.test.CustomerProto.CustomerList getDefaultInstanceForType() {
        return com.querybuilder.test.CustomerProto.CustomerList.getDefaultInstance();
      }
      
      public boolean isInitialized() {
        return result.isInitialized();
      }
      public com.querybuilder.test.CustomerProto.CustomerList build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }
      
      private com.querybuilder.test.CustomerProto.CustomerList buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public com.querybuilder.test.CustomerProto.CustomerList buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");
        }
        if (result.customers_ != java.util.Collections.EMPTY_LIST) {
          result.customers_ =
            java.util.Collections.unmodifiableList(result.customers_);
        }
        com.querybuilder.test.CustomerProto.CustomerList returnMe = result;
        result = null;
        return returnMe;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.querybuilder.test.CustomerProto.CustomerList) {
          return mergeFrom((com.querybuilder.test.CustomerProto.CustomerList)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(com.querybuilder.test.CustomerProto.CustomerList other) {
        if (other == com.querybuilder.test.CustomerProto.CustomerList.getDefaultInstance()) return this;
        if (!other.customers_.isEmpty()) {
          if (result.customers_.isEmpty()) {
            result.customers_ = new java.util.ArrayList<com.querybuilder.test.CustomerProto.Customer>();
          }
          result.customers_.addAll(other.customers_);
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10: {
              com.querybuilder.test.CustomerProto.Customer.Builder subBuilder = com.querybuilder.test.CustomerProto.Customer.newBuilder();
              input.readMessage(subBuilder, extensionRegistry);
              addCustomers(subBuilder.buildPartial());
              break;
            }
          }
        }
      }
      
      
      // repeated .mavenlauncher.Customer customers = 1;
      public java.util.List<com.querybuilder.test.CustomerProto.Customer> getCustomersList() {
        return java.util.Collections.unmodifiableList(result.customers_);
      }
      public int getCustomersCount() {
        return result.getCustomersCount();
      }
      public com.querybuilder.test.CustomerProto.Customer getCustomers(int index) {
        return result.getCustomers(index);
      }
      public Builder setCustomers(int index, com.querybuilder.test.CustomerProto.Customer value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.customers_.set(index, value);
        return this;
      }
      public Builder setCustomers(int index, com.querybuilder.test.CustomerProto.Customer.Builder builderForValue) {
        result.customers_.set(index, builderForValue.build());
        return this;
      }
      public Builder addCustomers(com.querybuilder.test.CustomerProto.Customer value) {
        if (value == null) {
          throw new NullPointerException();
        }
        if (result.customers_.isEmpty()) {
          result.customers_ = new java.util.ArrayList<com.querybuilder.test.CustomerProto.Customer>();
        }
        result.customers_.add(value);
        return this;
      }
      public Builder addCustomers(com.querybuilder.test.CustomerProto.Customer.Builder builderForValue) {
        if (result.customers_.isEmpty()) {
          result.customers_ = new java.util.ArrayList<com.querybuilder.test.CustomerProto.Customer>();
        }
        result.customers_.add(builderForValue.build());
        return this;
      }
      public Builder addAllCustomers(
          java.lang.Iterable<? extends com.querybuilder.test.CustomerProto.Customer> values) {
        if (result.customers_.isEmpty()) {
          result.customers_ = new java.util.ArrayList<com.querybuilder.test.CustomerProto.Customer>();
        }
        super.addAll(values, result.customers_);
        return this;
      }
      public Builder clearCustomers() {
        result.customers_ = java.util.Collections.emptyList();
        return this;
      }
    }
    
    static {
      com.querybuilder.test.CustomerProto.getDescriptor();
    }
    
    static {
      com.querybuilder.test.CustomerProto.internalForceInit();
    }
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_mavenlauncher_Customer_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_mavenlauncher_Customer_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_mavenlauncher_CustomerList_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_mavenlauncher_CustomerList_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\022CustomerList.proto\022\rmavenlauncher\"K\n\010C" +
      "ustomer\022\n\n\002id\030\001 \001(\003\022\021\n\tfirstName\030\002 \001(\t\022\020" +
      "\n\010lastName\030\003 \001(\t\022\016\n\006street\030\004 \001(\t\":\n\014Cust" +
      "omerList\022*\n\tcustomers\030\001 \003(\0132\027.mavenlaunc" +
      "her.CustomerB&\n\025com.querybuilder.testB\rC" +
      "ustomerProto"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_mavenlauncher_Customer_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_mavenlauncher_Customer_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_mavenlauncher_Customer_descriptor,
              new java.lang.String[] { "Id", "FirstName", "LastName", "Street", },
              com.querybuilder.test.CustomerProto.Customer.class,
              com.querybuilder.test.CustomerProto.Customer.Builder.class);
          internal_static_mavenlauncher_CustomerList_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_mavenlauncher_CustomerList_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_mavenlauncher_CustomerList_descriptor,
              new java.lang.String[] { "Customers", },
              com.querybuilder.test.CustomerProto.CustomerList.class,
              com.querybuilder.test.CustomerProto.CustomerList.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }
  
  public static void internalForceInit() {}
}