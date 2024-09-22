package ensias.ma.gl.secondyear.twentyfour.econutri.exception;

public class FieldExceptionDetail {
    
    private String fieldName;

    private ExceptionReason reason;

    private String description;


    public FieldExceptionDetail() {}

    public FieldExceptionDetail(String fieldName, ExceptionReason reason, String description) {
        this.fieldName = fieldName;
        this.reason = reason;
        this.description = description;
    }


    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public ExceptionReason getReason() {
        return this.reason;
    }

    public void setReason(ExceptionReason reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
