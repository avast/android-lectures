//
//  SearchView.swift
//  iosApp
//
//  Created by Lukáš Prokop on 06.01.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct SearchView: View {
    @State private var username: String = ""
    
    var body: some View {
        VStack {
            TextField(text: $username, prompt: Text("Username")) {
                Text("Username")
            }.textFieldStyle(.roundedBorder)
            NavigationLink(destination: UserScreen(username: username)) {
                Text("Search")
            }
        }.padding()
    }
}

struct SearchView_Previews: PreviewProvider {
    static var previews: some View {
        SearchView()
            .previewDisplayName("Default - en")
        SearchView()
            .environment(\.locale, .init(identifier: "cs"))
            .previewDisplayName("Czech")
    }
}
