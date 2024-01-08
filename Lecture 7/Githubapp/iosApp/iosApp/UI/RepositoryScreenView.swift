//
//  RepositoryScreenView.swift
//  iosApp
//
//  Created by Lukáš Prokop on 07.01.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct RepositoryScreenView: View {
    var repository: GithubRepository
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(repository.name)
                .font(.title)
            Text(repository.owner.login)
                .font(.subheadline)
            Text(repository.description_)
            Spacer()
        }.padding()
            .navigationTitle(repository.name)
    }
}

#Preview {
    RepositoryScreenView(repository: InMemoryRepository.companion.AvastRepositories[0])
}
