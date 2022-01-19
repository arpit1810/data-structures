import java.util.*;
class j7
{

    public static void printSegmentTree(SegmentTreeNode node)
    {
        if(node == null)
        return;
        printSegmentTree(node.left);
        System.out.println(node);
        printSegmentTree(node.right);
    }

    public static int query(int lowPrice, int highPrice, SegmentTreeNode root)
    {   
        System.out.println(root);
        if(root == null)
        return -1;

        if(lowPrice <= root.lowPrice && highPrice >= root.highPrice)
        {
            return root.maxRating;
        }

        if(lowPrice > root.highPrice || highPrice < root.lowPrice)
        return -1;

        return Math.max(
            query(lowPrice, highPrice, root.left),
            query(lowPrice, highPrice, root.right)
        );
    }


    public static SegmentTreeNode createSegmentTree(int low, int high, ArrayList<Node> nodeList)
    {
        if(low > high)
        return null;

        if(low == high)
        {
            return new SegmentTreeNode(nodeList.get(low).price, nodeList.get(low).price, nodeList.get(low).rating, null, null);
        }

        int mid = (low + high)/2;

        SegmentTreeNode left = createSegmentTree(low, mid, nodeList);
        SegmentTreeNode right = createSegmentTree(mid+1, high, nodeList);
        
        return new SegmentTreeNode(
                left.lowPrice, right.highPrice, Math.max(left.maxRating, right.maxRating), left, right
        );
    }

    public static void main(String args[])
    {
        int[] price = {1000, 100,5000, 10000, 7000};
        int[] rating = {5,4,3,2,1};

        ArrayList<Node> nodeList = new ArrayList<>();

        for(int i=0; i<price.length; ++i)
        {
            nodeList.add(new Node(price[i], rating[i]));
        }
        Collections.sort(nodeList, new Comparator<Node>(){
            public int compare(Node n1, Node n2)
            {
                return n1.price - n2.price;
            }
        });
        System.out.println(nodeList);

        SegmentTreeNode root = createSegmentTree(0, nodeList.size()-1, nodeList);
        printSegmentTree(root);
        System.out.println("\n\n");
        System.out.println(root);
        System.out.println(query(1, 50, root));
        System.out.println("\n\n");
        System.out.println(query(1, 1000, root));
    }
}

class Node
{
    int price;
    int rating;

    public Node(int price, int rating)
    {
        this.price = price;
        this.rating = rating;
    }

    public String toString()
    {
        return  "[" + price + "," + rating + "]";
    }
}


class SegmentTreeNode
{
    int lowPrice;
    int highPrice;
    int maxRating;
    SegmentTreeNode left;
    SegmentTreeNode right;

    public SegmentTreeNode(int lowPrice, int highPrice, int maxRating, SegmentTreeNode left, SegmentTreeNode right)
    {
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
        this.maxRating = maxRating;
        this.left = left;
        this.right = right;
    }

    public String toString()
    {
        return "[" + lowPrice + "," + highPrice + "," + maxRating + "]";
    }
}




