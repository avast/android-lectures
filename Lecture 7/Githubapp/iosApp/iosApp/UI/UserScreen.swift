//
//  UserScreen.swift
//  iosApp
//
//  Created by Lukáš Prokop on 06.01.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared
import os

struct UserScreen: View {
    
    @MainActor class UserViewModel: ObservableObject {
        
        @Published var user: User? = nil
        @Published var repositories: [GithubRepository]? = [GithubRepository]()
        
        let repository = NetworkRepository()
        
        func load(username: String) async {
            do {
                user = try await repository.getUser(username: username)
                repositories = try await repository.getUserRepository(username: username)
            } catch {
                os_log("Missing data", type: .error)
            }
        }
    }
    
    @StateObject private var viewModel = UserViewModel()
    var username: String = ""

    var body: some View {
        VStack {
            let user = viewModel.user
            if let user {
                UserView(user: user).padding(.horizontal)
                    .navigationTitle(username)
            }
            
            let repositories = viewModel.repositories
            Spacer()
            if let repositories {
                List(repositories, id: \.id) { repository in
                    NavigationLink{
                        RepositoryScreenView(repository: repository)
                    } label: {
                        RepositoryItemView(repository: repository)
                    }
                }.background(.red)
            }
        }.task {
            await viewModel.load(username: username)
        }
    }
}

#Preview {
    UserScreen(username: "avast")
}
