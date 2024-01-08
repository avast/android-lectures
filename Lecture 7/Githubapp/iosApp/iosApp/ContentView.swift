import SwiftUI
//import shared

struct ContentView: View {

	var body: some View {
        NavigationStack {
            SearchView()
                .navigationTitle("Github app")
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
