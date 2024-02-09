package HLD.ConsistentHashing;

public class Client {

    public static void main(String[] args) {
        String[] A=new String[]{"ADD","ASSIGN","ASSIGN","ASSIGN","ADD","ASSIGN","ASSIGN","ASSIGN","ADD","ASSIGN","ASSIGN","ASSIGN","ADD","ASSIGN","ASSIGN","ASSIGN","REMOVE","ASSIGN","ASSIGN","ASSIGN","REMOVE","ASSIGN","ASSIGN","ASSIGN","REMOVE","ASSIGN","ASSIGN"};
        String[] B=new String[]{"INDIA","VLVL","OXXV","HHGN","RUSSIA","AWNF","SPHI","FXKT","CHINA","JXZU","BWPK","JYWN","GERMANY","ZKYK","HLQZ","BRMS","INDIA","FMVA","NPJO","GACA","RUSSIA","ZMWM","XVUA","IDUW","CHINA","EHWW","KROX"};
        int[] C=new int[]{431,563,223,761,197,409,31,223,769,619,991,613,139,797,547,821,859,131,577,269,2,499,599,29,449,13,337};
        int[] d=new int[C.length];
        Solution sol=new Solution();

        sol.solve(A,B);

        System.out.println("xyz");

    }
}
