//
//  RepositoryView.swift
//  iosApp
//
//  Created by Lukáš Prokop on 06.01.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct RepositoryItemView: View {
    var repository: GithubRepository
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(repository.name)
                .font(.title)
            Text(repository.description_)
                .font(.subheadline)
        }.padding()
    }
}

#Preview {
    RepositoryItemView(repository: InMemoryRepository.companion.AvastRepositories[0])
}
