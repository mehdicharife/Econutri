package ensias.ma.gl.secondyear.twentyfour.econutri.request;


public class SimpleOrderTransitionRequest {
    
    private Long orderId;

    private String newState;


    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getNewState() {
        return this.newState;
    }

    public void setNewState(String newState) {
        this.newState = newState;
    }
    
}
