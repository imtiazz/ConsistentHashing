package HLD.ConsistentHashing;

import java.util.*;

public class Solution {

    static class Pair {
        int serverHash;
        String machineName;

        Pair(int serverHash, String machineName) {
            this.serverHash = serverHash;
            this.machineName = machineName;
        }
    }

    public int[] solve(String[] A, String[] B) {
        int[] result = new int[A.length];
        Map<Integer, String> serverMap = new TreeMap<>();
        Map<Integer, List<Pair>> machineMap = new TreeMap<>();

        for (int i = 0; i < A.length; i++) {
            String operation = A[i];
            String key = B[i];
            int hash = userHash(key);
            switch (operation) {
                case "ADD":
                    addServer(result, serverMap, machineMap, i, key, hash);
                    break;
                case "REMOVE":
                    removeServer(result, serverMap, machineMap, i, hash);
                    break;
                case "ASSIGN":
                    assignServer(result, serverMap, machineMap, i, key, hash);
                    break;
            }
        }
        return result;
    }

    private void addServer(int[] result, Map<Integer, String> serverMap, Map<Integer, List<Pair>> machineMap, int index, String key, int hash) {
        Set<Integer> serverSet;
        int count;
        serverMap.put(hash, key);
        if (serverMap.size() == 1) {
            // when current server is first server being added
            result[index] = 0;
            return;
        }
        serverSet = serverMap.keySet();
        int prevServer = -1;
        for (int val : serverSet) {
            if (val < hash) {
                prevServer = val;
            } else break;
        }
        if (prevServer != -1) {
            count = 0;
            for (Map.Entry<Integer, List<Pair>> integerPairEntry : machineMap.entrySet()) {
                int machineKey = integerPairEntry.getKey();
                if (machineKey >= prevServer + 1 && machineKey <= hash) {
                    List<Pair> pairs = integerPairEntry.getValue();
                    for (Pair p : pairs) {
                        p.serverHash = hash;
                        count++;
                    }
                    integerPairEntry.setValue(pairs);
                }
            }
            result[index] = count;
            return;
        }
        // assign last server id
        for (int val : serverSet) {
            prevServer = val;
        }
        count = 0;
        for (Map.Entry<Integer, List<Pair>> integerPairEntry : machineMap.entrySet()) {
            int machineKey = integerPairEntry.getKey();
            if ((machineKey >= prevServer + 1 && machineKey < 360) ||
                    (machineKey >= 0 && machineKey <= hash)) {
                List<Pair> pairs = integerPairEntry.getValue();
                for (Pair p : pairs) {
                    p.serverHash = hash;
                    count++;
                }
                integerPairEntry.setValue(pairs);
            }
        }
        result[index] = count;
    }

    private void removeServer(int[] result, Map<Integer, String> serverMap, Map<Integer, List<Pair>> machineMap, int index, int hash) {
        int count;
        Set<Integer> serverSet;
        serverSet = serverMap.keySet();
        int nextServer = -1;
        for (int val : serverSet) {
            if (val > hash) {
                nextServer = val;
                break;
            }
        }
        if (nextServer == -1) {
            // if no next server, then assign all request coming to this server to first server
            if (serverSet.stream().findFirst().isPresent()) {
                nextServer = serverSet.stream().findFirst().get();
            }
        }
        count = 0;
        for (Map.Entry<Integer, List<Pair>> integerPairEntry : machineMap.entrySet()) {
            List<Pair> pairs = integerPairEntry.getValue();
            for (Pair p : pairs) {
                if (p.serverHash == hash) {
                    count++;
                    p.serverHash = nextServer;
                }
            }
            integerPairEntry.setValue(pairs);
        }
        result[index] = count;
        serverMap.remove(hash);
    }

    private void assignServer(int[] result, Map<Integer, String> serverMap, Map<Integer, List<Pair>> machineMap, int index, String key, int hash) {
        Set<Integer> set = serverMap.keySet();
        boolean found = false;
        int serverHash = 0;
        for (int val : set) {
            if (val >= hash) {
                found = true;
                serverHash = val;
                break;
            }
        }
        if (!found) {
            if (set.stream().findFirst().isPresent()) {
                serverHash = set.stream().findFirst().get();
            }
        }
        if (machineMap.containsKey(hash)) {
            machineMap.get(hash).add(new Pair(serverHash, key));
        } else {
            List<Pair> pairs = new ArrayList<>();
            pairs.add(new Pair(serverHash, key));
            machineMap.put(hash, pairs);
        }
        result[index] = hash;
    }

    int userHash(String username) {

        int p = 31;
        long n = 360;
        long hashCode = 0;
        long p_pow = 1;
        for (int i = 0; i < username.length(); i++) {
            char character = username.charAt(i);
            hashCode = (hashCode + (character - 'A' + 1) * p_pow) % n;
            p_pow = (p_pow * p) % n;
        }
        return (int) hashCode;

    }
}
