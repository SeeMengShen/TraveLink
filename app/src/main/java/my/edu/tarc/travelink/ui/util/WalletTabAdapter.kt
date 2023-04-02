package my.edu.tarc.travelink.ui.util

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import my.edu.tarc.travelink.ui.wallet.WalletFragment

class WalletTabAdapter (val items: List<Fragment>, activity: WalletFragment): FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return items[position]
    }
}