package com.example.pal.ui.homeScreens.settings.terms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pal.R
import com.example.pal.databinding.FragmentTermsBinding
import com.example.pal.databinding.TermsItemLayoutBinding
import com.example.pal.ui.homeScreens.home.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared

@AndroidEntryPoint
class TermsFragment : Fragment() {
    private var binding: FragmentTermsBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTermsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // the back button
        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }

        val termsItems: List<TermsItem> = listOf(
            TermsItem(
                getString(R.string.Terms_and_Conditions), getString(R.string.Terms_last_updated) +
                        "\n\n" +
                        getString(R.string.Read_Terms_Carfully_warning)
            ),
            TermsItem(
                getString(R.string.Definitions),
                getString(R.string.For_the_purposes_of_these_Terms_and_Conditions) +
                        "\n\n" + getString(R.string.Terms_Application_Means_Definitions) +
                        "\n\n" +
                        getString(R.string.Terms_Application_Store_Means_Definitions) +
                        "\n\n" +
                        getString(R.string.Terms_Affiliate_Means_Definitions_part_one_of_three) +
                        "\"" +
                        getString(R.string.Terms_Affiliate_Means_Definitions_part_two_of_three) +
                        "\"" +
                        getString(R.string.Terms_Affiliate_Means_Definitions_part_three_of_three) +
                        "\n\n" +
                        getString(R.string.Terms_Country_Means_Definitions) +
                        "\n\n" +
                        getString(R.string.Terms_Company_Means_Definitions_part_one_of_Seven) +
                        " \"" +
                        getString(R.string.Terms_Company_Means_Definitions_part_two_of_Seven) +
                        "\", \"" +
                        getString(R.string.Terms_Company_Means_Definitions_part_three_of_Seven) +
                        "\", \"" +
                        getString(R.string.Terms_Company_Means_Definitions_part_four_of_Seven) +
                        "\" " +
                        getString(R.string.Terms_Company_Means_Definitions_part_five_of_Seven) +
                        " \"" +
                        getString(R.string.Terms_Company_Means_Definitions_part_six_of_Seven) +
                        "\" " +
                        getString(R.string.Terms_Company_Means_Definitions_part_seven_of_Seven) +
                        "\n\n" +
                        getString(R.string.Terms_Device_Means_Definitions) +
                        "\n\n" +
                        getString(R.string.Terms_Service_Means_Definitions) +
                        getString(R.string.Terms_Terms_And_Conditions_Means_Definitions_part_one_of_Three) +
                        "\"" +
                        getString(R.string.Terms_Terms_And_Conditions_Means_Definitions_part_two_of_Three) +
                        "\"" +
                        getString(R.string.Terms_Terms_And_Conditions_Means_Definitions_part_three_of_Three) +
                        "\n\n" +
                        getString(R.string.Terms_Third_Party_Means_Definitions) +
                        "\n\n" +
                        getString(R.string.Terms_You_Means_Definitions)
            ),
            TermsItem(
                getString(R.string.Acknowledgment),
                getString(R.string.Terms_Acknowledgment_Phrase_one_outOf_Five) +
                        "\n\n" +
                        getString(R.string.Terms_Acknowledgment_Phrase_two_outOf_Five) +
                        "\n\n" +
                        getString(R.string.Terms_Acknowledgment_Phrase_three_outOf_Five) +
                        "\n\n" +
                        getString(R.string.Terms_Acknowledgment_Phrase_four_outOf_Five) +
                        "\n\n" +
                        getString(R.string.Terms_Acknowledgment_Phrase_five_outOf_Five)
            ),
            TermsItem(
                getString(R.string.Links_to_Other_Websites),
                getString(R.string.Terms_LinksToOtherWebsites_Phrase_one_outOf_Three) +
                        "\n\n" +
                        getString(R.string.Terms_LinksToOtherWebsites_Phrase_two_outOf_Three) +
                        "\n\n" +
                        getString(R.string.Terms_LinksToOtherWebsites_Phrase_three_outOf_Three)
            ),
            TermsItem(
                getString(R.string.Termination),
                getString(R.string.Terms_Termination_Phrase_one_outOf_Two) +
                        "\n\n" +
                        getString(R.string.Terms_Termination_Phrase_two_outOf_Two)
            ),
            TermsItem(
                getString(R.string.Limitation_of_Liability),
                getString(R.string.Terms_LimitationOfLiability_Phrase_one_outOf_Three) +
                        "\n\n" +
                        getString(R.string.Terms_LimitationOfLiability_Phrase_two_outOf_Three) +
                        "\n\n" +
                        getString(R.string.Terms_LimitationOfLiability_Phrase_three_outOf_Three)
            ),
            TermsItem(
                "\"" +
                        getString(R.string.AS_IS) +
                        "\" " +
                        getString(R.string.and) +
                        " \"" +
                        getString(R.string.AS_AVAILABLE) +
                        "\" " +
                        getString(R.string.Disclaimer),
                getString(R.string.Terms_Disclaimer_phrase_one_OutOf_Four) +
                        "\"" +
                        getString(R.string.AS_IS) +
                        "\" " +
                        getString(R.string.and) +
                        " \"" +
                        getString(R.string.AS_AVAILABLE) +
                        "\" " +
                        getString(R.string.Terms_Disclaimer_phrase_two_OutOf_Four) +
                        "\n\n" +
                        getString(R.string.Terms_Disclaimer_phrase_three_OutOf_Four) +
                        "\n\n" +
                        getString(R.string.Terms_Disclaimer_phrase_four_OutOf_Four)
            ),
            TermsItem(
                getString(R.string.Governing_Law),
                getString(R.string.Terms_GoverningLaw_Description)
            ),
            TermsItem(
                getString(R.string.Disputes_Resolution),
                getString(R.string.Terms_DisputeResolution)
            ),
            TermsItem(
                getString(R.string.For_European_Union_Users),
                getString(R.string.Terms_For_European_Union_Users)
            ),
            TermsItem(
                getString(R.string.United_States_Legal_Compliance),
                getString(R.string.Terms_United_States_Legal_Compliance_one_OutOf_Three) +
                        "\"" +
                        getString(R.string.Terms_United_States_Legal_Compliance_two_OutOf_Three) +
                        "\"" +
                        getString(R.string.Terms_United_States_Legal_Compliance_three_OutOf_Three)
            ),
            TermsItem(getString(R.string.Severability), getString(R.string.Terms_Severability)),
            TermsItem(getString(R.string.Waiver), getString(R.string.Terms_Waiver)),
            TermsItem(
                getString(R.string.Translatiom_Interpretation),
                getString(R.string.Terms_Translatiom_Interpretation)
            ),
            TermsItem(
                getString(R.string.Changes_to_These_Terms_and_Conditions),
                getString(R.string.Terms_Changes_to_These_Terms_and_Conditions_one_OutOf_Two) +
                        "\n\n" +
                        getString(R.string.Terms_Changes_to_These_Terms_and_Conditions_two_OutOf_Two)
            ),
            TermsItem(
                getString(R.string.Contact_Us), getString(R.string.Terms_Contact_Us_one_OutOf_Two) +
                        "\n\n" +
                        getString(R.string.Terms_Contact_Us_two_OutOf_Two)
            ),
            TermsItem("", getString(R.string.Generated_using_TermsFeed_Privacy_Policy_Generator)),

            )
        binding.termsRecycler.adapter = TermsAdapter(termsItems)
        binding.termsRecycler.layoutManager = LinearLayoutManager(requireContext())
    }
}