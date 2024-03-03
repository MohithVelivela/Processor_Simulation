package processor.pipeline;
import processor.Processor;
import java.util.*;
public class Control_Unit {
    Processor containingProcessor;
    public Control_Unit(Processor containingProcessor){
        this.containingProcessor = containingProcessor;
    }
    public Map<String, String> map_operation_name = new HashMap<String, String>() {{
        put("00000", "add");
        put("00001", "addi");
        put("00010", "sub");
        put("00011", "subi");
        put("00100", "mul");
        put("00101", "muli");
        put("00110", "div");
        put("00111", "divi");
        put("01000", "and");
        put("01001", "andi");
        put("01010", "or");
        put("01011", "ori");
        put("01100", "xor");
        put("01101", "xori");
        put("01110", "slt");
        put("01111", "slti");
        put("10000", "sll");
        put("10001", "slli");
        put("10010", "srl");
        put("10011", "srli");
        put("10100", "sra");
        put("10101", "srai");
        put("10110", "load");
        put("10111", "store");
        put("11000", "jmp");
        put("11001", "beq");
        put("11010", "bne");
        put("11011", "blt");
        put("11100", "bgt");
        put("11101", "end");
    }};



    boolean St;
    boolean Ld;
    boolean Beq;
    boolean Bgt;
    boolean Blt;
    boolean Bne;
    boolean End;
    boolean Wb;
    boolean Jmp;  // Equivalent to Unconditional Branch in SimpleRisc
    boolean Add;
    boolean Sub;
    boolean Mul;
    boolean Div;
    boolean Slt;   // Strictly Less than
    boolean Sll;   //logical left shit
    boolean Srl;   //logical right shift
    boolean Sra;   //Arithmetic right shift
    boolean Or;
    boolean And;
    boolean Xor;

    String Opcode_str = "";
    int rd;
    

    public void setOpcode(String opcode){Opcode_str = opcode;}
    public String getOpcode(){
        return Opcode_str;
    }
    public void setrd(int x){
        this.rd = x;
    }
    public int getrd(){
        return rd;
    }
    public boolean isSt(){return St;}

    public void setSt(boolean st) {
        St = st;
    }
    public boolean isLd(){return Ld;}

    public void setLd(boolean ld) {
        Ld =  ld;
    }
    public boolean isBeq(){return Beq;}

    public void setBeq(boolean beq) {
            Beq =  beq;
    }

    public boolean isBgt(){return Bgt;}

    public void setBgt(boolean bgt) {
        Bgt =  bgt;
    }

    public boolean isBlt(){return Blt;}

    public void setBlt(boolean blt) {
        Blt =  blt;
    }

    public boolean isBne(){return Bne;}

    public void setBne(boolean bne) {
        Bne =  bne;
    }

    public boolean isEnd(){return End;}

    public void setEnd(boolean end) {
        End =  end;
    }

    public boolean isWb() {
        return Wb;
    }

    public void setWb(boolean wb) {
        Wb = wb;
    }

    public boolean isJmp() {
        return Jmp;
    }

    public void setJmp(boolean jmp) {
        Jmp = jmp;
    }

    public boolean isAdd() {
        return Add;
    }

    public void setAdd(boolean add) {
        Add = add;
    }

    public boolean isSub() {
        return Sub;
    }

    public void setSub(boolean sub) {
        Sub = sub;
    }

    public boolean isMul() {
        return Mul;
    }

    public void setMul(boolean mul) {
        Mul = mul;
    }

    public boolean isDiv() {
        return Div;
    }

    public void setDiv(boolean div) {
        Div = div;
    }

    public boolean isSlt() {
        return Slt;
    }

    public void setSlt(boolean slt) {
        Slt = slt;
    }

    public boolean isSll() {
        return Sll;
    }

    public void setSll(boolean sll) {
        Sll = sll;
    }

    public boolean isSrl() {
        return Srl;
    }

    public void setSrl(boolean srl) {
        Srl = srl;
    }

    public boolean isSra() {
        return Sra;
    }

    public void setSra(boolean sra) {
        Sra = sra;
    }

    public boolean isOr() {
        return Or;
    }

    public void setOr(boolean or) {
        Or = or;
    }

    public boolean isAnd() {
        return And;
    }

    public void setAnd(boolean and) {
        And = and;
    }

    public boolean isXor() {
        return Xor;
    }

    public void setXor(boolean xor) {
        Xor = xor;
    }
}

