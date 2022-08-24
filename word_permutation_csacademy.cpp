#include <iostream>
#include <string>
#include <vector>
#include <algorithm>
#include <map>

using namespace std;

int main() {
    int n;
    cin >> n;
    
    string arr[n];
    string cparr[n];
    for(int i = 0; i <n; i++)
    {
        cin >> arr[i];
    }
    for (int i=0; i<n; i++)
            cparr[i] = arr[i];
    sort(cparr, cparr + sizeof cparr / sizeof cparr[0]);
    
    // empty map container
    map<string, int> ind;
    for (int i=0; i<n; i++)
        ind.insert(pair<string, int>(cparr[i], i+1));
        
    /*for(const auto& elem : ind)
    {
        std::cout << elem.first << " " << elem.second << "\n";
    }*/
    for (auto& elem: arr)
    {
        auto it = ind.find(elem);
        std::cout << it->second << " ";
    }
    return 0;
}