import java.util.*;

class BFS{

public void bfstofind (int arr[][],int V, int source, int dest){

    boolean vis[] = new boolean[V];
    Queue<Integer> q = new LinkedList<>();
    q.add(source);
    vis[source] = true;
    
    System.out.print("BFS: ");
    while(q.size()!=0){


        int node = q.poll();
        System.out.print(node+" ");
        
        if(node == dest){
            return;
        }

        for(int index = 0;index<V;index++){

            if(arr[node][index]==1 && vis[index] == false){
                if(index == dest){
                    System.out.print(index+" ");
                    return;
                }
                q.add(index);
                vis[index] = true;
            }
        }

    }

}

}

class DFS{

    public void dfstofind(int arr[][], int V, int source, int dest){

        Stack <Integer> S = new Stack <Integer>();
        boolean vis[] = new boolean[V];

        S.push(source);
        vis[source] = true;

        System.out.print("\n"+"DFS: ");

        while(S!=null){

            int node = S.pop();

            System.out.print(node+" ");

            if(node == dest){
                return;
            }

            for(int index = 0; index<V;index++){
                if(arr[node][index]==1&&vis[index]==false){
                    
                    if(index==dest){
                        System.out.print(index+" ");
                        return;
                    }

                    S.push(index);
                    vis[index] = true;


                }
            }

        }

    }

}

class BFSandDFS {


public static void main(String args[]){

    int arr[][] = {{0,1,1,1,0,0}, {1,0,0,0,1,0},{1,0,0,1,0,1},{1,0,1,0,1,1},{0,1,0,1,0,1},{0,0,1,1,1,0}};

    BFS obj1 = new BFS();

    DFS obj2 = new DFS();

    obj1.bfstofind(arr, 6, 0, 5);
    
    obj2.dfstofind(arr, 6, 0, 5);


}


}